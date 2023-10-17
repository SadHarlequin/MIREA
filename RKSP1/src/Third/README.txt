Была использована очередь Queue<FutureTask<File>>. Потокобезопасность обеспечивается с помощью функции future.cancel(true)
