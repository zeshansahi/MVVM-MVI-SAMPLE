package life.league.challenge.kotlin

import android.util.Base64
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.api.ApiService
import life.league.challenge.kotlin.fragments.PostRepository
import life.league.challenge.kotlin.util.MockResponseFileReader
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.HashMap

@RunWith(JUnit4::class)
class PostListApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: PostRepository
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {

        server.start(8000)

        var BASE_URL = BuildConfig.baseUrl//"https://engineering.league.dev/challenge/api/"

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

        repository = PostRepository(service)
    }
    @Test
    fun testApiSuccessLogin() {
        mockedResponse = MockResponseFileReader("json/login.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.apiService.login(
            life.league.challenge.kotlin.util.Base64.encodeToString(
                "hell:world".toByteArray(),
                Base64.NO_WRAP
            )
        ) }
        val json = gson.toJson(response.body())

        val resultResponse = JsonParser.parseString(json)
        val expectedresponse = JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(response)
        Assert.assertTrue(!resultResponse.equals(expectedresponse))
    }
    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("json/postlist.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val headerMap = HashMap<String, String>()
        headerMap["x-access-token"] = "FC5E038D38A57032085441E7FE7010B0"
        headerMap["accept"] = "application/json"
        val response = runBlocking { repository.getPostList(headerMap) }
        val json = gson.toJson(response.body())

        val resultResponse = JsonParser.parseString(json)
        val expectedresponse = JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.equals(expectedresponse))
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}