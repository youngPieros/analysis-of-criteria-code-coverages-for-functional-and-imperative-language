{-# LANGUAGE OverloadedStrings #-}

module DTO
( CourseSummary(..)
, toDTO
) where


import qualified Data.ByteString.Lazy.Char8 as C
import Data.Aeson



toDTO :: ToJSON a => a -> String
toDTO obj = (C.unpack . encode) $ obj


data CourseSummary = CourseSummary { code :: String
                                   , name :: String
                                   , instructor :: String
                                   }

instance ToJSON CourseSummary where
    toJSON (CourseSummary code name instructor) =
        object ["code" .= code, "name" .= name, "instructor" .= instructor]

