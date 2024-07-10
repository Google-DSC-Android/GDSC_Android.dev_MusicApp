
![GDSC Android dev  Bootcamp'24 Vertical color](https://github.com/Aditys018/GDSC_ADB_BustinGieber/assets/100122761/07e20cd9-b639-47c8-9817-7cf08e915313)

# BustinGieber is a Music Streaming Android Application which we are going to develop in this year's GDSC Android Development Bootcamp.
# I have created this repo for improving your UNDERSTANDING of this project.
# Step by step approach for this entire application is provided in this doc.
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


# BustinGieber
## It is a music streaming android application named after Justin Bieber ;)


## version control

#### connect android studio to GitHub


Create your GitHub Account here https://github.com/

Create a new project in your Android studio

to connect it with GitHub Create a new GitHub repo 

(Make sure Git and GitHub are installed from settings>properties>Plugin)

open the terminal in your Android Studio

Give the following Commands


| Command | Description|
| :--------  | :-------------------------------- |
|  cd/path/to/your/project   |  Change Directory to Your Project  |
| git init  | Initialize Empty Git Repository |
|  git add .  |  Add files to git |
| git commit -m "initial commit"  | Commit your changes |
|  git remote add origin (repo. URL)  |  Add GitHub as remote  |
| git push -u origin master| Push your changes |


 
 
 
Steps to create the project

STEP 1 = Search for a better free music streaming API

STEP 2 = Add required dependencies

STEP 3 = Create Data Class

Step 4 = Create the API interface

Step 5 = Fetch data in main activity

Step 6 = Create Adapter

Step 7 = Link the RecyclerView with Adapter


STEP 1 =
First of all Give the internet permissions inside your androidmanifest.xml file
```Kotlin
  <uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

Go to this link  https://rapidapi.com/collection/music-apis
(make sure you are signed in)

We will be using Deezer API For this project

The link for the API IS   https://rapidapi.com/deezerdevs/api/deezer-1/

select kotlin OKHTTP
Test Endpoint checkout the keys and data
checkout the API here  https://jsonformatter.curiousconcept.com/#




STEP 2 = Adding the dependencies

we will be adding Retrofit dependency and GSON Converter in our project 

//retrofit dependency
visit link  https://square.github.io/retrofit/
(add latest version)

//GSON Converter
Visit link  https://github.com/google/gson
(add latest version)

Make sure to Sync the project

```Kotlin
//All the dependencies are as follows

 //Retrofit dependency
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    //GSON Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //picasso Library
    implementation("com.squareup.picasso:picasso:2.8")
```


STEP 3 = Create Data Class

Now we will convert JSON Data into data class

For that copy the data from https://rapidapi.com/deezerdevs/api/deezer-1/  and paste it in the Android studio inside kotlin data class file from JSON

(If the extension is not installed inside your android studio then go to File>Settings>Plugins>Search for JSON to Kotlin class and install the extension)

Format the copied data>Give class Name as MyData>Generate the data class


STEP 4 = Creating the API Interface

Create a new Kotlin Interface in your main Java Package and name it as ApiInterface 



```Kotlin
interface ApiInterface {

    @Headers("X-RapidAPI-Key:6b5dfcce33msh06e06ec4af4f8b2p18a89ejsndad351702d1d",
            "X-RapidAPI-Host:deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q")query: String): Call<MyData>
}

```

Now we will be writing retrofit Builder in order to get all the information in our mainActivity
go to your mainActivity.kt file

```Kotlin
 val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
             val retrofitData= retrofitBuilder.getData("eminem")
```

Now we have to write the enque method in mainActivity.kt and based on the API Call it will decide if it is a success or a failure 

```Kotlin
 retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList=response.body()?.data!!
                val textView= findViewById<TextView>(R.id.helloText)
               textView.text= dataList.toString()
                Log.d("TAG: onResponse" , "onResponse" + response.body())
                
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
```
so, til now the JSON Data is successfully added into our project AKA Virtual/Physical Device.

STEP 6 = Create Adapter

inside your main package create a new class named as MyAdapter
```Kotlin
class MyAdapter(val context: Activity, val dataList:List<MyData>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {                      
class MyViewHolder(itemView : view): RecyclerView.viewHolder(){
}

}
```
Implement Members after that it will create 3  members i.e onCreateViewHolder, getItemCount and onBindViewHolder all of these are very important.

After that we will be creating our dsign file go to Drwable>Layout>right click >Layout resource file > name it as each_item

Here we will be dsigning the UI Part for our application you can refer the code here: https://github.com/Aditys018/GDSC_Android.dev_MusicApp/tree/master/app/src/main/res/layout

now again come back to myAdapter class we will write the code for myViewHolder class we will create variables for the cardview and initialize the variables
```Kotlin
 class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val play: ImageButton
        val pause: ImageButton

        init{
            image= itemView.findViewById(R.id.musicImage)
            title= itemView.findViewById(R.id.musicTitle)
            play= itemView.findViewById(R.id.btnPlay)
            pause= itemView.findViewById(R.id.btnPause)
        }
    }
```
you can put myViewHolder class at the bottom of MyAdapter class

Now we will be writing a code for onCreateViewHolder, it creates the view in case the layout manager fails to create view for the data
```Kotlin
val itemView= LayoutInflater.from(context).inflate(R.layout.each_item, parent, false);return MyViewHolder(itemView)
```

Now we will be writing a code for onBindViewHolder
```Kotlin
 //populate the data into the view
        val currentData= dataList[position]

        val mediaPlayer= MediaPlayer.create(context, currentData.preview.toUri())
        holder.title.text= currentData.title


        Picasso.get().load(currentData.album.cover).into(holder.image);

        holder.play.setOnClickListener{
            mediaPlayer.start()
        }
        holder.pause.setOnClickListener{
            mediaPlayer.stop()
        }

```
Now we will go within our activity_main.xml create a linearlayout and inside that linearLayout add the recyclerView

After that we will go in our MainActivity to attach the adapter with recyclerView
so above oncreate  we will be writing 
```Kotlin
 lateinit var myrecyclerview:RecyclerView
    lateinit var myAdapter: MyAdapter
```
now initialize the recyclerView inside onCreate 
```Kotlin
  myrecyclerview= findViewById(R.id.recyclerView)
```
Now we will change our onResponse inside it we will write
```Kotlin
  val dataList=response.body()?.data!!
//                val textView= findViewById<TextView>(R.id.helloText)
//                textView.text= dataList.toString()

                myAdapter= MyAdapter(this@MainActivity, dataList)
                myrecyclerview.adapter=myAdapter
                myrecyclerview.layoutManager=LinearLayoutManager(this@MainActivity)
                Log.d("TAG: onResponse", "onResponse: " +response.body())
```

```Kotlin
CONGRATULATIONS YOUR APPLICATION IS FINALLY CREATED
```



 # final functional output which will play music from the deezer API

 
 ![Music](https://github.com/Google-DSC-Android/GDSC_Android.dev_MusicApp/assets/100122761/2bbfff40-4849-471a-9b6e-61c7e2d69211)

















