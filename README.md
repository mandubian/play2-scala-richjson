# Play2 Rich Json Tool

> Some sugar syntax to write your Play2/Scala JSON in a more compact way 

    val richJson = "this" : ( "is" \: ( "my" @@ ( "times" \: 1 ) @@ "play2" @@ "tool" ) )
    
## Example

Take the following JSON:

    {
    	key1 : { key11: "alpha11", key12: "alpha12" },
    	key2 : { key21 : 23, key22 : true },
    	key3 : {
    		key31 : [
    			"key311",
    			67.23,
    			"key313",
    			{ key311: "alpha311", key312: "alpha312" }	
    		]
    	} 
    }


With Play2/Scala default API, you would write: 

    def index = Action {
      import play.api.libs.json._
      import play.api.libs.json.Json._

      val json = 
        JsObject(Seq(
          ("key1", JsObject(Seq(
        		  ("key11", JsString("alpha11")),
        		  ("key12", JsString("alpha12"))
          	))
          ),
          ("key2", JsObject(Seq(
        		  ("key21", JsNumber(23)),
        		  ("key22", JsBoolean(true))
          	))
          ),
          ("key3", JsObject(Seq(
              ("key31", JsArray(List(
                  JsString("key311"), 
                  JsNumber(67.23),
                  JsString("key313"),
                  JsObject(Seq(
                      ("key311", JsString("alpha311")),
                      ("key312", JsString("alpha312"))
                      ))
                ))
              )
            ))  
          )
        ))
        
      Ok(toJson(json))
    }

With RichJson, you can write: 

    def index = Action {
      import play.api.libs.json._
      import play.api.libs.json.Json._
      import play.tools.richjson.RichJson._

      val richJson = 
        "key1" \: ( 
          "key11" \: "alpha11" ++ 
          "key12" \: "alpha12"
        ) ++ 
        "key2" \: (
          "key21" \: 23 ++
          "key22" \: true
        ) ++
        "key3" \: (
          "key31" \: ( 
              "key311" @@ 
              67.23 @@ 
              "key313" @@ 
              (   "key311" \: "alpha311" ++ 
                  "key312" \: "alpha312" ) )
        )
        
      Ok(toJson(richJson))
    }    

Isn't it more compact ?


## Doc

### Build RichJson Jar (till this is in an online repo)

#### Git clone the project

    git clone git://github.com/mandubian/play2-scala-richjson.git


#### Build RichJson

    > sbt compile publish-local
    It should publish into your local Ivy repository. For ex: "/Users/USERNAME/.ivy2/local" on a Mac


#### Add RichJson dependency to your `$YOUR_PLAY_APP/project/Build.scala`

    val appDependencies = Seq(
      // Add your project dependencies here,
      "play.tools.richjson" %% "play-2.0-scala-richjson-tool" % "0.1-SNAPSHOT"
    )
    
    // need to update to your own repo (put the right directory)
    val moduleRepo = Resolver.file("local repository", file("/Users/USERNAME/.ivy2/local"))(Resolver.ivyStylePatterns)

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += moduleRepo,
      resolvers += ("typesafe snapshots" at "http://repo.typesafe.com/typesafe/snapshots/")
    )

### Import the implicits conversion in your local context (for ex, an action in a controller)

      // classic JSON imports
      import play.api.libs.json._
      import play.api.libs.json.Json._
      // RichJson imports
      import play.tools.richjson.RichJson._

### Convert your Json into RichJson

Here are the correspondences between Json & RichJson syntax :

* `:` is replaced by `\:`
* `{ k : "v" }` is replaced by `( "k" \: "v" )`
* `{ k1 : "v1", k2 : "v2" }` is replaced by `( "k1" \: "v1" ++ "k2" \: "v2" )`
* `[ v1, v2, v3 ]` is replaced by `( v1 @@ v2 @@ v3 )`

RichJson returns a classic Play2/Scala _JsObject_

    val richJson:JsObject = "key" \: "value"
    val richJsonArray:JsArray = "k1" @@ 23 @@ "k2"

Have Fun
