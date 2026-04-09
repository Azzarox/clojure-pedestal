(ns routes
  (:require [interceptors :refer :all]))

;; Using #' it evaluates the Var and not the function behind it ... this way i dont need to evaluate depending functions and stuff when i change something
(def routes
  #{["/greet" :get [coerce-body-interceptor
                    content-negotiation-interceptor
                    #'greet-handler] :route-name :greet]
    ["/echo" :get echo :route-name :echo]})
