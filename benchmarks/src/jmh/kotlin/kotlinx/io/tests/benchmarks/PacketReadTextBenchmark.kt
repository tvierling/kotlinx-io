package kotlinx.io.tests.benchmarks

import kotlinx.io.core.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

/**
 # Run complete. Total time: 00:01:30

Benchmark                              Mode  Cnt   Score    Error  Units
PacketReadTextBenchmark.measureInput   avgt   15  63.205 ±  1.503  ms/op
PacketReadTextBenchmark.measurePacket  avgt   15  48.074 ±  0.313  ms/op
PacketReadTextBenchmark.measureString  avgt   15  47.622 ±  0.290  ms/op
PacketReadTextBenchmark.measureInput     ss   15  78.627 ± 11.173  ms/op
PacketReadTextBenchmark.measurePacket    ss   15  55.670 ± 10.376  ms/op
PacketReadTextBenchmark.measureString    ss   15  59.169 ± 14.386  ms/op
 */

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 15)
@BenchmarkMode(Mode.SingleShotTime, Mode.AverageTime)
//@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class PacketReadTextBenchmark {

    @Benchmark
    fun measurePacket() = testData.copy().readText()

    @Benchmark
    fun measureInput() = (testData.copy() as Input).readText()

    @Benchmark
    fun measureString() = String(testBytes)

    companion object {
        private val testStrings = listOf(
            "古時科特林島為芬蘭人的土地，在芬蘭語中稱為「雷圖薩里島」（Retusaari）。" +
                    "1155年芬蘭被瑞典征服，瑞典和俄羅斯人的諾夫哥羅德公國互相爭奪科特林島，" +
                    "最後於13世紀時確定歸屬於諾夫哥羅德公國。後來諾夫哥羅德公國於1478年被莫斯科公國征服，" +
                    "於是科特林島成為莫斯科公國的領土。但1613年時科特林島又被瑞典與卡累利阿共同瓜分，" +
                    "直到1703年時俄羅斯帝國的彼得大帝才在大北方戰争中重新從瑞典手中奪回科特林島。" +
                    "由於科特林島的軍事戰略地位非常重要，所以彼得大帝奪回科特林島後，便在島上建立喀琅施塔得市，" +
                    "作為防衛京師聖彼得堡的要塞。" +
                    "18世紀的20年代起，科特林島更成為俄國海軍指揮所和波羅的海艦隊的基地。",
            """
            Kotlin
            Kotlin Logo
            Designed by     JetBrains
            Developer       JetBrains and open source contributors
            First appeared  2011
            Stable release
            1.2.60 / August 1, 2018; 11 days ago[1]
            Typing discipline       static, inferred
            Platform        Outputs Java virtual machine bytecode and JavaScript source code
            OS      Any supporting JVM or JavaScript interpreter
            License Apache 2
            Filename extensions     .kt, .kts
            Website kotlinlang.org
            Influenced by
            Java, Scala, Groovy, C#, Gosu, JavaScript
            Kotlin is a statically typed programming language that runs on the Java virtual machine and also can be compiled to JavaScript source code or use the LLVM compiler infrastructure. Its primary development is from a team of JetBrains programmers based in Saint Petersburg, Russia.[2] While the syntax is not compatible with Java, the JVM implementation of the Kotlin standard library is designed to interoperate with Java code and is reliant on Java code from the existing Java Class Library, such as the collections framework[3]. Kotlin uses aggressive type inference to determine the type of values and expressions for which type has been left unstated. This reduces language verbosity relative to Java, which demands often entirely redundant[citation needed] type specifications prior to version 10.
            """
        )

        private val testData = buildPacket {
            while (size < 32 * 1024 * 1024) {
                testStrings.forEach { writeStringUtf8(it) }
            }
        }

        private val testBytes = testData.copy().readBytes()
    }
}
