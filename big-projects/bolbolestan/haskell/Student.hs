{-# LANGUAGE OverloadedStrings #-}

module Student
( Student(..)
) where


import Data.Aeson



data Student = Student { studentId :: String
                       , name :: String
                       , enteredAt :: Int
                       } | NullStudent

instance Eq Student where
    NullStudent == NullStudent = True
    Student s1 _ _ == Student s2 _ _ = s1 == s2
    _ == _ = False

instance ToJSON Student where
    toJSON (Student studentId name enteredAt) =
        object ["studentId" .= studentId, "name" .= name, "enteredAt" .= enteredAt]

