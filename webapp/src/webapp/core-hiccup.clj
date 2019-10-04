(ns webapp.core
  (:require [hiccup.core :as h]
            [hiccup.form :as hf]))


(h/html
  [:h1 "Hello, World"])

(h/html
  [:h1 {:class "foo"} "Hello, World"])

(h/html
  [:h1#foo.bar "Hello"])


(h/html
  [:section
   [:p "Paragraph 1"]
   [:p "Paragraph 2"]])

(h/html
  [:ul
   (for [item ["foo" "bar" "baz"]]
     [:li item])])

(h/html
  [:fieldset
   [:label "Name"]
   (hf/text-field "name" "Value")])

(h/html
  [:fieldset
   (hf/drop-down "favorite-color"
                 ["red" ["Blue" "blue"] ["Verde" "green"]])])

(defn layout [content & options]
  (let [opts (apply hash-map options)]
    (h/html
      [:html
        [:head
          [:title(get opts :title "Hello, World")]]
        [:body [:main content]]])))

(layout [:p "Hello"] :title "Hi")
