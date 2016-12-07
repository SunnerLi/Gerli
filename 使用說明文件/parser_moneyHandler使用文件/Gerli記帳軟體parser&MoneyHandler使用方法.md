# Parser和MoneyHandler使用文件

有鑑於蛤蠣做的超級精美的使用文件，所以我也來做，不過不精美哈哈。
<br/>
<br/>

把code加到你的專案裡
----------
方法就跟蛤蠣說的一樣，不過我這邊資料夾的名稱叫做parser，你就整個資料夾複製到你java專案最底下的那一層就ok，大概的結構像這樣，**注意，我有把mondyHandler移到資料夾裡面囉**：
```
./ -----com.example.demo
		|
		+-----MainActivity.java
		|
		+-----parse
				|
				+-----Parser
				|
				+-----Record
				|
				+-----RemoteParser
				|
				+-----MoneyHandler
```
<br/>
如果你把蛤蠣的東西也加進來，大概專案結構會長的像這樣，搭配蛤蠣的圖應該滿好懂的：
```
./ -----com.example.demo
		|
		+-----MainActivity.java
		|
		+-----parse
		|		|
		|		+-----Parser
		|		|
		|		+-----Record
		|		|
		|		+-----RemoteParser
		|		|
		|		+-----MoneyHandler
		|
		+-----gerli.handsomeboy
				|
				+-----gerlisqlitedemo
				|
				+-----gerliUnit
```
<br/>

使用方法 - 1
----------
首先，你要把權限加入到menifest檔案裡面，大概像這樣：

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```
<br/>
接著，在程式一開始的時候創建parser物件，大概像這樣：
```java
	Parser parser = new Parser()
```
<br/>
創建完後，就可以在你需要的時候，呼叫parse函式，使用方法向這樣如下。
```java
	parser.parse("The book is 50 dollars", Parser.sentence)
```
<br/>
這個函式第二個參數請一律像上面的方式這樣傳，在parseDevelop這個示範專案裡面可以看到，上面三個按鈕都是傳這個參數`Parser.sentence`進去。至於最下面的按鈕傳的不一樣，是因為這樣可以在手機上叫電腦把程式結束，但一般情況不會有這樣的設計出現，因此不需要。
當然你也可以用一個最簡便的作法，就像我的例子一樣，將前面兩步合併變成：
```java
	new Parser().parse("The book is 50 dollars", Parser.sentence);
```
<br/>
最後你要獲取parse的結果。Parser class中並不會在parse完後直接回傳record object的結果，你必須要自己再另外呼叫：
```java
	Record record = new Record()
	record = parser.get()
```
<br/>

使用方法 - 2
----------
加入權限仍然是需要的。
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```
<br/>
接著，創建MoneyHandler物件：
```java
	MoneyHandler handler = new MoneyHandler()
```
<br/>

最後，簡單的一個步驟，呼叫work
```java
	handler.work("The book is 50 dollars")
```