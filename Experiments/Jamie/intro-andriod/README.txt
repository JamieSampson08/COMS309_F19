Tutorial: https://www.journaldev.com/13629/okhttp-android-example-tutorial
Dummy Endpoint: https://reqres.in/


### Test
1. Ran on a Galaxy S9, Andriod 9.0 (as an fyi for any visual issuses)
2. Start Emulator
3. Try to click "Toast" or "Next" (should see an error toast)
4. Type in a name, clikc "Toast" see toast updated with name entered
5. Type in a email, click "Next" (new screen pops up)


6. In Andriod Studio, got to "view" "Tools Window" "Logcat" and search for "morpheus"
You should see something similar to: 
2019-09-08 01:05:09.319 8869-9106/? D/TAG: {"name":"morpheus","job":"leader","id":"417","createdAt":"2019-09-08T06:05:11.174Z"}

7. Should see name in header "Hello" + name
8. Should see tutorial post/get request response in the text view body
