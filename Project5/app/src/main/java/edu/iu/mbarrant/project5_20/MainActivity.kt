package edu.iu.mbarrant.project5_20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import edu.iu.mbarrant.project5_20.databinding.ActivityMainBinding
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var translationTV: TextView // Declare translationTV as a class member

    // Initialize the English to German translator
    private val optionsEtoG = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.GERMAN)
        .build()

    // Initialize the English to Spanish translator
    private val optionsEtoS = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.SPANISH)
        .build()

    // Initialize the Spanish to German translator
    private val optionsStoG = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.SPANISH)
        .setTargetLanguage(TranslateLanguage.GERMAN)
        .build()

    // Initialize the Spanish to English translator
    private val optionsStoE = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.SPANISH)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()

    // Initialize the German to Spanish translator
    private val optionsGtoS = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.GERMAN)
        .setTargetLanguage(TranslateLanguage.SPANISH)
        .build()

    // Initialize the German to English translator
    private val optionsGtoE = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.GERMAN)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()

    private val englishGermanTranslator: Translator = Translation.getClient(optionsEtoG)
    private val englishSpanishTranslator: Translator = Translation.getClient(optionsEtoS)
    private val spanishGermanTranslator: Translator = Translation.getClient(optionsStoG)
    private val spanishEnglishTranslator: Translator = Translation.getClient(optionsStoE)
    private val germanSpanishTranslator: Translator = Translation.getClient(optionsGtoS)
    private val germanEnglishTranslator: Translator = Translation.getClient(optionsGtoE)

    // Flag to keep track of model download status
    private var englishToGerm = false
    private var englishToSpan = false
    private var spanToGerm = false
    private var spanToEng = false
    private var germToEng = false
    private var germToSpan = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //initialize Default string values for language value holders
        var selectedLanguage = ""
        var selectedLanguageTo = ""
        // Initialize translationTV as a class member
        translationTV = binding.translatedTextView

        //initializing views
        val userInputTV = binding.editText

        // Download the translation models
        //english models:
        downloadTranslationModel()
        downloadEngToSpanModel()
        //spanish models:
        downloadSpanToEngModel()
        downloadSpanToGermModel()
        //german models:
        downloadGermToEngModel()
        downloadGermToSpanModel()


        // TextWatcher for user input
        userInputTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userInputString = s.toString()
                Log.d("TextWatcher", "Text changed: $userInputString")

                // Check if both "English" and "German" are selected and the model is downloaded
                if (selectedLanguage == "English" && selectedLanguageTo == "German" && englishToGerm) {
                    translateText(userInputString)
                }
                // Check if both "English" and "Spanish" are selected and the model is downloaded
                if (selectedLanguage == "English" && selectedLanguageTo == "Spanish" && englishToSpan) {
                    translateText2(userInputString)
                }
                // Check if both "Spanish" and "English" are selected and the model is downloaded
                if (selectedLanguage == "Spanish" && selectedLanguageTo == "English" && spanToEng) {
                    translateSpanToEng(userInputString)
                }
                // Check if both "Spanish" and "German" are selected and the model is downloaded
                if (selectedLanguage == "Spanish" && selectedLanguageTo == "German" && spanToGerm) {
                    translateSpanToGerm(userInputString)
                }
                // Check if both "German" and "Spanish" are selected and the model is downloaded
                if (selectedLanguage == "German" && selectedLanguageTo == "Spanish" && germToSpan) {
                    translateSpanToGerm(userInputString)
                }
                // Check if both "German" and "English" are selected and the model is downloaded
                if (selectedLanguage == "German" && selectedLanguageTo == "English" && germToEng) {
                    translateSpanToGerm(userInputString)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //initialize radio groups
        //use binding
        val ogLang = binding.ogRadioGroup
        val langTo = binding.transToRadioGroup

        //radio group for the original Language "ogLang"
        ogLang.setOnCheckedChangeListener { _, checkedId ->
            // Handle language click
            when (checkedId) {
                R.id.ogEnglish -> {
                    // english selected
                    selectedLanguage = "English"
                    Log.d("origin Lang Activity", "Button Clicked: English")
                }
                R.id.ogSpanish -> {
                    // spanish selected
                    selectedLanguage = "Spanish"
                    Log.d("origin Lang Activity", "Button Clicked: Spanish")
                }
                R.id.ogGerman -> {
                    // german selected
                    selectedLanguage = "German"
                    Log.d("origin Lang Activity", "Button Clicked: German")
                }
                R.id.ogAuto ->{
                    selectedLanguage = "Auto"
                    Log.d("origin Lang Activity", "Button Clicked: Auto")
                }
            }
        }

        //radio group for the language to translate to
        langTo.setOnCheckedChangeListener { _, checkedId ->
            // Handle language button click
            when (checkedId) {
                R.id.startEnglish -> {
                    // english selected
                    selectedLanguageTo = "English"
                    Log.d("Translate to Activity", "Button Clicked: English")
                }
                R.id.startSpanish -> {
                    // spanish selected
                    selectedLanguageTo = "Spanish"
                    Log.d("Translate to Activity", "Button Clicked: Spanish")
                }
                R.id.startGerman -> {
                    // german selected
                    selectedLanguageTo = "German"
                    Log.d("Translate to Activity", "Button Clicked: German")
                }
                R.id.startAuto ->{
                    selectedLanguage = "Auto"
                    Log.d("Translate to Activity", "Button Clicked: Auto")
                }
            }
        }
    }
    // Function to translate text
    //english to german
    private fun translateText(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        englishGermanTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }
    }

    //English to spanish
    private fun translateText2(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        englishSpanishTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }

    }

    //spanish to english
    private fun translateSpanToEng(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        spanishEnglishTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }

    }

    //spanish to german
    private fun translateSpanToGerm(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        spanishGermanTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }
    }

    //german to spanish
    private fun translateGermToSpan(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        germanSpanishTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }
    }

    //german to english
    private fun translateGermToEnglish(inputText: String) {

        Log.d("Translation", "Translating: $inputText") // Log inputText before translation

        germanEnglishTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                Log.d("Translation", "Translated Text: $translatedText") // Log translatedText
                // Translation successful.
                translationTV.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                Log.e("Translation Error", exception.toString())
            }
    }

    // Function to download the translation model
    private fun downloadTranslationModel() {
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        englishGermanTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                englishToGerm = true
                Log.d("Model EtoG downloaded good", "EtoG downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoG downloaded bad", "EtoG not downlded: ${exception.message}")
            }
    }

    private fun downloadEngToSpanModel(){
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        englishSpanishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                englishToSpan = true
                Log.d("Model EtoS downloaded good", "EtoS downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoS downloaded bad", "EtoS not downlded: ${exception.message}")
            }
    }

    private fun downloadSpanToGermModel(){
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        spanishGermanTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                spanToGerm = true
                Log.d("Model EtoS downloaded good", "EtoS downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoS downloaded bad", "EtoS not downlded: ${exception.message}")
            }
    }
    private fun downloadSpanToEngModel(){
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        spanishEnglishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                spanToEng = true
                Log.d("Model EtoS downloaded good", "EtoS downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoS downloaded bad", "EtoS not downlded: ${exception.message}")
            }
    }
    private fun downloadGermToEngModel(){
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        germanEnglishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                germToEng = true
                Log.d("Model EtoS downloaded good", "EtoS downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoS downloaded bad", "EtoS not downlded: ${exception.message}")
            }
    }
    private fun downloadGermToSpanModel(){
        val conditions = DownloadConditions.Builder()
            .requireWifi() // Adjust download conditions as needed
            .build()

        germanSpanishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                germToSpan = true
                Log.d("Model EtoS downloaded good", "EtoS downlded")
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Log.e("Model EtoS downloaded bad", "EtoS not downlded: ${exception.message}")
            }
    }


}