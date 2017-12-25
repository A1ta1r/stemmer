import java.io.File

val exePath = "mystem.exe"
val textPath = "sampletext.txt"
val arguments = "-csi" //получить разделитель предложений и подробный разбор слов
val part = "PART" //частица
val pr = "PR" //предлог
val spro = "SPRO" //местоименое-существительное
val apro = "APRO" //местоимение-прилагательное
val advpro = "ADVPRO" //местоимение-наречие
val conj = "CONJ" //союз
val invincibleParts = listOf(pr, spro, apro, advpro, conj, part)
var totalWeight = 0

fun main(args: Array<String>) {
    val myStem = MyStem(exePath, arguments, textPath)

    val annotatedText = myStem.getAnnotatedText()

    val weights = getWeights(annotatedText)

    val sentences = getSentences(annotatedText)

    val sentencesWeights = mutableMapOf<String, Double?>()

    for (x in sentences) {
        sentencesWeights.put(removeAnnotations(x), getSentenceWeight(x, weights))
    }

    val text = File(textPath).readText()
    val splitText = text.split(" ")
    totalWeight = splitText.count()

    println("Overall weight = $totalWeight")
    println("Enter weight value to filter sentences:")

    val filter = readLine()!!.toInt()

    val shrinked = mutableListOf<String>()

    for (x in sentencesWeights) {
        if (x.value!! > filter) {
            shrinked.add(x.key)
        }
    }

    shrinked.forEach { println(it) }

    println("Compression rate: ${shrinked.joinToString(" ").length / text.length.toDouble()}%")
}

fun getSentenceWeight(sentence: String, tokens: Map<String, Double?>): Double {
    var weight = 0.0
    for (word in sentence.split(" - ", " ", ". {\\s}")) {
        if (isValidPart(getPart(word), invincibleParts)) {
            val token = getToken(word)
            weight += tokens[token]!!
        }
    }
    return weight
}

fun getWeights(text: String): MutableMap<String, Double?> {
    val splitText = text.split(" - ", " ", ". {\\s}")
    val totalWeight = splitText.count()
    val wordWeight = 1.0 / totalWeight
    val tokens = mutableMapOf<String, Double?>()
    for (word in splitText) {
        val token = getToken(word)
        if (isValidPart(getPart(word), invincibleParts)) {
            if (tokens.containsKey(token)) {
                tokens[token] = tokens[token]?.plus(1)
            } else {
                tokens.put(token, wordWeight)
            }
        }
    }
    var sum = 0.0
    tokens.toList().forEach { sum += it.second!! }
    println("$totalWeight, $sum")
    return tokens
}

fun getToken(lexedWord: String): String {
    val splitWord = lexedWord.split("{", "=", ",", "\\s}", ".")
    if (splitWord.count() <= 2) {
        println(lexedWord)
    }
    return splitWord[1]
}

fun getPart(lexedWord: String): String {
    val splitWord = lexedWord.split("{", "=", ",", "\\s}", ".")
    if (splitWord.count() <= 2) {
        println(lexedWord)
    }
    return splitWord[2]
}

fun isValidPart(part: String, dict: List<String>): Boolean {
    return !dict.contains(part)
}

fun getSentences(text: String): List<String> {
    return text.split(". {\\s}")
}

fun removeAnnotations(text: String): String {
    return text.replace(Regex("\\{.*?}"), "")
}