import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.URL
import kotlin.system.exitProcess

inline fun <reified T> Gson.parseJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)

private var currencyList: List<Currency> = ArrayList()

fun getDataFromServer() {
    try {
        val baseUrl = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/"
        val url = URL(baseUrl)
        val urlConnection = url.openConnection()
        val inputStream = urlConnection.getInputStream()
        val responseReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(responseReader)
        var response = bufferedReader.readLine()
        val stringBuilder = StringBuilder()
        while (response != null) {
            stringBuilder.append(response)
            response = bufferedReader.readLine()
        }
        inputStream.close()

        val gson = Gson()
        currencyList = gson.parseJson<List<Currency>>(stringBuilder.toString())
    } catch (e: FileNotFoundException) {
        println(e)
    }
}

fun getAllCurrencyCourses() {
    for (currency: Currency in currencyList) {
        println(currency.toString())
    }
}

fun isNum(variable: String): Boolean {
    return variable.all { c: Char -> c.isDigit() }
}

fun main() {


    getDataFromServer()

    println(
        "*** Assalomu alaykum! O'zbekiston Respublikasi Markaziy bankiga hush kelibsiz! ***\n\nBaza yangilanish sanasi: ${currencyList[0].date}"
    )


    while (true) {
        println(
            "Menu:" + "\n\t1. Barcha valyuta kurlari bilan tanishish" + "\n\t2. Valyuta ayirboshlash" + "\n\t3. Valyutani ko'rish" + "\n\t0. Chiqish " + "\nIltimos raqam kiriting "
        )
        val action = readln().toString()
        when (action.toInt()) {
            1 -> {
                getAllCurrencyCourses()
            }

            2 -> {
                currencyExchange()
            }

            3 -> {
                getCurrencyCourse()
            }

            0 -> {
                exitProcess(0)
            }

            else -> {
                println("bug =0=")
            }
        }
    }
}

fun getCurrencyCourse() {
    println("Valyuta kodini kiriting(m-n: USD, EUR, RUB):")
    val code = readln().toString()

    var foundC = false

    for (currency: Currency in currencyList) {
        if (currency.currencyCode.equals(code.uppercase())) {
            println("${currency.currencyName}\nBahosi: ${currency.rate} UZS")
            foundC = true
        }
    }
    if (!foundC) {
        println("Bunday valyuta kodi topilmadi...")
    }
}

fun currencyExchange() {
    println("Valyuta kodini kiriting(m-n: USD, EUR, RUB):")
    val code = readln().toString()

    var foundC = false

    for (currency: Currency in currencyList) {
        if (currency.currencyCode.equals(code.uppercase())) {
            println("${currency.currencyName}\nBahosi: ${currency.rate} UZS")
            foundC = true

            println("Qancha pul ayirboshlaysiz ?")
            while (true) {
                println("Son kiriting: ")
                val money = readln().toString()
                if (isNum(money)) {
                    val result = money.toFloat() / currency.rate.toString().toFloat()
                    println("$money UZS = $result ${currency.currencyCode}")
                    break
                }
            }

        }
    }
    if (!foundC) {
        println("Bunday valyuta kodi topilmadi...")
    }
}
