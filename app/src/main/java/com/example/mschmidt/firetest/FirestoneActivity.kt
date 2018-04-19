package com.example.mschmidt.firetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mschmidt.firetest.Constants.FirebaseConstants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_firestone.*


class FirestoneActivity : AppCompatActivity() {

    lateinit var db: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestone)

        db = FirebaseFirestore.getInstance().document("IceCreams/Flavours")

        val store = findViewById<View>(R.id.storeBtn) as Button

        store.setOnClickListener {
            view: View? -> store ()
        }
        var i = 1
        db.collection("Blue Ice")
                .get()
                .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>{
                    override fun onComplete(task: Task<QuerySnapshot>) {
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                if (document.id == FirebaseConstants.DOCUMENT_CHILD) {
                                    document.data as HashMap
                                }
                                Log.d("TAG", document.id + " => " + document.data)
                                Log.d("TAG", document.data["Ingrediant-1"].toString() + document.data["Ingrediant-2"].toString())
                                flavTxt.text = FirebaseConstants.DOCUMENT_CHILD
                                ing1Txt.text = document.data["Ingrediant-1"].toString()
                                ing2Txt.text = document.data["Ingrediant-2"].toString()
                                ing3Txt.text = document.data["Ingrediant-3"].toString()
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.exception)
                        }
                    }

                })
    }



    private fun store() {
        val flavourTxt = findViewById<View>(R.id.flavourTxt) as EditText
        val ingrediant1Txt = findViewById<View>(R.id.collectionsTxt) as EditText
        val ingrediant2Txt = findViewById<View>(R.id.in2Txt) as EditText
        val ingrediant3Txt = findViewById<View>(R.id.in3Txt) as EditText

        var flavour = flavourTxt.text.toString().trim()
        val ingrediant1 = ingrediant1Txt.text.toString().trim()
        val ingrediant2 = ingrediant2Txt.text.toString().trim()
        val ingrediant3 = ingrediant3Txt.text.toString().trim()

        if (!flavour.isEmpty() && !ingrediant1.isEmpty() && !ingrediant2.isEmpty() && !ingrediant3.isEmpty()) {
            try {
                val items = HashMap<String, Any>()
                items.put("Ingrediant-1", ingrediant1)
                items.put("Ingrediant-2", ingrediant2)
                items.put("Ingrediant-3", ingrediant3)
                db.collection(flavour).document("Ingrediants").set(items).addOnSuccessListener {
                    void: Void? -> Toast.makeText(this, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Please fill up the fields :(", Toast.LENGTH_LONG).show()
        }

    }

}
