# alp

Starting two repls, one for server one for client.

```clojure
(def srv (serv 12000))
; => Start command
; => #'alp.core/srv
```

if you telnet to localhost:12000, you will see `test` printed out to console every 3 seconds because of
this; https://github.com/bcambel/alp/blob/master/src/alp/core.clj#L70 CHECK. Our server is working

The next step is to connect the client

```clojure
(def c @(client "localhost" 12000))
; << stream: {:type "splice", :sink {:pending-puts 0, :drained? false, :buffer-size 0, :permanent? false, :type ; ; "manifold", :sink? true, :closed? false, :pending-takes 1, :buffer-capacity 0, :source? true}, :source {:pending-puts 0, :drained? false, :buffer-size 0, :permanent? false, :type "manifold", :sink? true, :closed? false, :pending-takes 0, :buffer-capacity 0, :source? true}} >>
;
; server side prints out
;  {:remote-addr 0:0:0:0:0:0:0:1, :server-port 12000, :server-name localhost}
@(ms/put! c 1) ; => true

; server side prints out (2). CHECK.

@(ms/take! c)
; holds the current thread, no values returned.
c
;<< stream: {:type "splice", :sink {:pending-puts 0, :drained? false, :buffer-size 0, :permanent? false, :type "manifold", :sink? true, :closed? false, :pending-takes 1, :buffer-capacity 0, :source? true}, :source {:pending-puts 0, :drained? false, :buffer-size 0, :permanent? false, :type "manifold", :sink? true, :closed? false, :pending-takes 1, :buffer-capacity 0, :source? true}} >>

@(ms/try-take! c 1000) ; => nil

; nothing is returned in a second.
```



Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
