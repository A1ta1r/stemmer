fun main(args: Array<String>) {

    val exePath = "mystem.exe"
    val textPath = "sampletext.txt"
    val elitePath = "elitewords.txt"
    val arguments = "-csi"
    val splitter = "{\\s}"
    val part = "PART" //частица
    val pr = "PR" //предлог
    val spro = "SPRO" //местоименое-существительное
    val apro = "APRO" //местоимение-прилагательное
    val advpro = "ADVPRO" //местоимение-наречие
    val conj = "CONJ" //союз
    val invincibleParts = listOf(pr, spro, apro, advpro, conj, part)

    val myStem = MyStem(exePath, arguments, textPath)

    val annotatedText = myStem.getAnnotatedText()

    for (x in annotatedText) {
        println(x)
    }
}