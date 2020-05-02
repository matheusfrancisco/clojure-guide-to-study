;; Concurrency and Parallelism Concepts

;Concurrent and parallel programming involves a lot of messy details at all
;levels of program execution, from the hardware to the operating system to
;programming language libraries to the code that springs from your heart
;and lands in your editor. But before you worry your head with any of those
;details, in this section I’ll walk through the high-level concepts that
;surround concurrency and parallelism

;Managing Multiple Tasks vs. Executing Tasks
;Simultaneously
;Concurrency refers to managing more than one task at the same time. Task
;just means “something that needs to get done,” and it doesn’t imply
;anything regarding implementation in your hardware or software. We can
;illustrate concurrency with the song “Telephone” by Lady Gaga. Gaga
;sings,
;I cannot text you with a drink in my hand, eh
;Here, she’s explaining that she can only manage one task (drinking). She
;flat-out rejects the suggestion that she can manage more than one task.
;However, if she decided to process tasks concurrently, she would sing,
;I will put down this drink to text you, then put my phone away and
;continue drinking, eh

;Parallelism refers to executing more than one task at the same time. If
;Madame Gaga were to execute her two tasks in parallel, she would sing,
;I can text you with one hand while I use the other to drink, eh
;Parallelism is a subclass of concurrency: before you execute multiple
;tasks simultaneously, you first have to manage multiple tasks.


;Parallelism refers to executing more than one task at the same time. If
;Madame Gaga were to execute her two tasks in parallel, she would sing,
;I can text you with one hand while I use the other to drink, eh
;Parallelism is a subclass of concurrency: before you execute multiple
;tasks simultaneously, you first have to manage multiple tasks.

;It’s important to distinguish parallelism from distribution. Distributed
;computing is a special version of parallel computing where the processors
;are in different computers and tasks are distributed to computers over a
;network. It’d be like Lady Gaga asking Beyoncé, “Please text this guy while
;I drink.” Although you can do distributed programming in Clojure with the
;aid of libraries, this book covers only parallel programming, and here I’ll
;use parallel to refer only to cohabiting processors. If you’re interested in
;distributed programming, check out Kyle Kingsbury’s Call Me Maybe
;series at https://aphyr.com


;Blocking and Asynchronous Tasks


;One of the major use cases for concurrent programming is for blocking
;operations. Blocking really just means waiting for an operation to finish.
;You’ll most often hear it used in relation to I/O operations, like reading a
;file or waiting for an HTTP request to finish. Let’s examine this using the
;concurrent Lady Gaga example.
;If Lady Gaga texts her interlocutor and then stands there with her phone
;in her hand, staring at the screen for a response and not drinking, then you
;would say that the read next text message operation is blocking and that
;these tasks are executing synchronously.
;If, instead, she tucks her phone away so she can drink until it alerts her
;by beeping or vibrating, then the read next text message task is not blocking
;and you would say she’s handling the task asynchronously


;Clojure Implementation: JVM Threads


;I've been using the term task in an abstract sense to refer to a series
; of related operations without regard for how a computer might implement the
; task concept. For example, texting is a task that consists of a series of 
; related operatoins that are totally separate from the opeartions involved in
; pouring a drink into your face.


; What's a thread?

;I’m glad you asked! A thread is a subprogram. A program can have many
;threads, and each thread executes its own set of instructions while enjoying
;shared access to the program’s state.
;Thread management functionality can exist at multiple levels in a
;computer. For example, the operating system kernel typically provides
;system calls to create and manage threads. The JVM provides its own
;platform-independent thread management functionality, and since Clojure
;programs run in the JVM, they use JVM threads. You’ll learn more about
;the JVM in Chapter 12.


;This makes the program nondeterministic. You
;can’t know beforehand what the result will be because you can’t know the
;execution order, and different execution orders can yield different results.

;The third Concurrency Goblin is what I’ll call the dwarven berserker
;problem (aka deadlock). Imagine four berserkers sitting around a roughhewn, circular wooden table comforting each other. “I know I’m distant
;toward my children, but I just don’t know how to communicate with them,”
;one growls. The rest sip their coffee and nod knowingly, care lines creasing
;their eye places


;Futures, Delays, and Promises


;Futures, delays, and promises are easy, lightweight tools for concurrent
;programming. In this section, you’ll learn how each one works and how to
;use them together to defend against the reference cell Concurrency Goblin
;and the mutual exclusion Concurrency Goblin. You’ll discover that,
;although simple, these tools go a long way toward meeting your
;concurrency needs.
;They do this by giving you more flexibility than is possible with serial
;code. When you write serial code, you bind together these three events:
;• Task definition
;• Task execution
;• Requiring the task’s result



;Futures
(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print immediately")
;Thread/sleep tells the current thread to just sit on its bum and do
;nothing for the specified number of milliseconds. Normally, if you evaluated
;Thread/sleep in your REPL, you wouldn’t be able to evaluate any other
;statements until the REPL was done sleeping; the thread executing your
;REPL would be blocked. However, future creates a new thread and places
;each expression you pass it on the new thread, including Thread/sleep,
;allowing the REPL’s thread to continue, unblocked.
;You can use futures to run tasks on a separate thread and then forget
;about them, but often you’ll want to use the result of the task. The future
;function returns a reference value that you can use to request the result. The
;reference is like the ticket that a dry cleaner gives you: at any time you can
;use it to request your clean dress, but if your dress isn’t clean yet, you’ll
;have to wait. Similarly, you can use the reference value to request a future’s
;result, but if the future isn’t done computing the result, you’ll have to wait


;Requesting a future’s result is called dereferencing the future, and you do
;it with either the deref function or the @ reader macro. A future’s result
;value is the value of the last expression evaluated in its body. A future’s
;body executes only once, and its value gets cached. Try the following:

(let [result (future (println "this prints once")
                                    (+ 1 1))]
  (println "dref:" (deref result))
  (println "@:" @result))

; conjure/eval | (let [result (future (println "this prints once") (+ 1 1))] (println "dref:" (deref result)) (println "@:" @result))
; conjure/out | this prints once
; conjure/out | dref: 2
; conjure/out | @: 2
; conjure/ret ⤸


(let [result (future (Thread/sleep 3000)
                                  (+ 1 1))]
    (println "The result is:" @result)
    (println "It will be at least 3 seconds before I print"))

; conjure/eval | (let [result (future (Thread/sleep 3000) (+ 1 1))] (println "The result is:" @result) (println "It will be at least 3 seconds before I print"))
; conjure/out | The result is: 2
; conjure/out | It will be at least 3 seconds before I print
; conjure/ret ⤸

;Futures are a dead-simple way to sprinkle some concurrency on your
;program.
;On their own, they give you the power to chuck tasks onto other threads,
;which can make your program more efficient. They also let your program
;behave more flexibly by giving you control over when a task’s result is
;required.
;When you dereference a future, you indicate that the result is required
;right now and that evaluation should stop until the result is obtained. You’ll
;see how this can help you deal with the mutual exclusion problem in just a
;bit. Alternatively, you can ignore the result. For example, you can use
;futures to write to a log file asynchronously, in which case you don’t need to
;dereference the future to get any value back.
;The flexibility that futures give you is very cool. Clojure also allows you
;to treat task definition and requiring the result independently with delays
;and promises



;Delays

(def jackson-5-delay
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))


(force jackson-5-delay)
@jackson-5-delay


(def gimli-headshots ["serious.jpg" "fun.jpg" "paly.jpg"])

(defn email-user
    [email-address]
    (println "Sending headshot notification to" email-address))

(defn upload-document
    "Needs to be implemented"
    [headshot]
    true)

(let [notify (delay (email-user "and-my-axe@gmail.com"))]
  (doseq [headshot gimli-headshots]
    (future (upload-document headshot)
              (force notify))))


;; Promises

(def my-promise (promise))
(deliver my-promise (+ 1 2))
;#object [clojure.core$promise$reify__8501 236004830 {:status :ready, :val 3}]
@my-promise
;=> 3
;Here, you create a promise and then deliver a value to it. Finally, you
;obtain the value by dereferencing the promise. Dereferencing is how you
;express that you expect a result, and if you had tried to dereference mypromise without first delivering a value, the program would block until a
;promise was delivered, just like with futures and delays. You can only
;deliver a result to a promise once.
;One use for promises is to find the first satisfactory element in a
;collection of data. Suppose, for example, that you’re gathering ingredients
;to make your parrot sound like James Earl Jones. Because James Earl Jones
;has the smoothest voice on earth, one of the ingredients is premium yak
;butter with a smoothness rating of 97 or greater. You have a budget of $100
;for one pound.



(def yak-butter-internaticonal
  {:store "Yak Butter International"
   :price 90
   :smoothness 90})

(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smothness 83})


;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)


(defn satisfactory?
  "If the butter meets our criteria, return the butter, els e return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time (some (comp satisfactory? mock-api-call)
            [yak-butter-internaticonal butter-than-nothing baby-got-yak]))

; conjure/eval | (time (some (comp satisfactory? mock-api-call) [yak-butter-internaticonal butter-than-nothing baby-got-yak]))
; conjure/out | "Elapsed time: 3000.870844 msecs"
; conjure/ret ⤸
;{:store "Baby Got Yak", :price 94, :smoothness 99}


(time
  (let [butter-promise (promise)]
    (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
      (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
                (deliver butter-promise satisfactory-butter))))
    (println "And the winner is:" @butter-promise)))


;In this example, you first create a promise, @butter-promise, and then
;create three futures with access to that promise. Each future’s task is to
;evaluate a yak butter site and to deliver the site’s data to the promise if it’s
;satisfactory. Finally, you dereference @butter-promise, causing the
;program to block until the site data is delivered. This takes about one
;second instead of three because the site evaluations happen in parallel. By
;decoupling the requirement for a result from how the result is actually
;computed, you can perform multiple computations in parallel and save some
;time.
;You can view this as a way to protect yourself from the reference cell
;Concurrency Goblin. Because promises can be written to only once, you
;prevent the kind of inconsistent state that arises from nondeterministic reads
;and writes.
;You might be wondering what happens if none of the yak butter is
;satisfactory. If that happens, the dereference would block forever and tie up
;the thread. To avoid that, you can include a timeout:
;

(let [p (promise)]
  (deref p 100 "timed out"))
