(ns clock.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::output-time
 (fn [db]
   (:output-time db)))
