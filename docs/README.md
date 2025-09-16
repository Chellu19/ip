**BazingaBot 📝🤖**

BazingaBot is a simple command-line task management chatbot built in Java. It allows users to add, manage, and query tasks like Todos, Deadlines, and Events. The bot also supports automatic sorting of tasks by deadlines (C-Sort extension).

**Features**


* Add Todo: ```todo <description>```

* Add Deadline: ```deadline <description> /by <YYYY-MM-DD>```

* Add Event: ```event <description> /from <YYYY-MM-DDTHH:MM> /to <YYYY-MM-DDTHH:MM>```

* List Tasks: ```list```

* Mark/Unmark Tasks: ```mark <task number>, unmark <task number>```

* Delete Tasks: ```delete <task number>```

* Find Tasks by Keyword: ```find <keyword>```

* Exit: ```bye```


**Getting Started**

_Requirements_

1. [ ] Java 17+
2. [ ] Gradle 7+

Running the Bot

Clone the repository:

git clone <repo-url>
``` cd BazingaBot ```


Build the project (skip Checkstyle if errors occur):

``` ./gradlew build -x checkstyleMain -x checkstyleTest ```


Run the bot:

``` java -ea -jar build/libs/CS2103T.jar ```