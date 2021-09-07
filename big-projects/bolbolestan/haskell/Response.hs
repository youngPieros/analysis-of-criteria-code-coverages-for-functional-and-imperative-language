{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE DuplicateRecordFields #-}


module Response
( Response(..)
) where


import Data.Aeson

data Response =
    SuccessResponse String |
    SuccessfulEmptyResponse |
    ConflictOnAddCourse |
    RepetitiveStudentInsertion |
    StudentNotFound |
    OfferingNotFound |
    RepetitiveCourseError |
    EmptySchedule |
    MinimumUnitsError |
    MaximumUnitsError |
    ClassTimeCollisionError String String |
    ExamTimeCollisionError String String |
    FullCapacityError String |
    BadCommand


instance ToJSON Response where
    toJSON (SuccessResponse response) = object ["success" .= True, "data" .= response]
    toJSON SuccessfulEmptyResponse = object ["success" .= True, "data" .= ("" ++ "")]
    toJSON ConflictOnAddCourse = object ["success" .= False, "error" .= ("" ++ "This course has conflict in class time with current courses")]
    toJSON RepetitiveStudentInsertion = object ["success" .= False, "error" .= ("" ++ "REPETITIVE STUDENT ID")]
    toJSON StudentNotFound = object ["success" .= False, "error" .= ("" ++ "StudentNotFound")]
    toJSON OfferingNotFound = object ["success" .= False, "error" .= ("" ++ "OfferingNotFound")]
    toJSON RepetitiveCourseError = object ["success" .= False, "error" .= ("" ++ "RepetitiveCourseError")]
    toJSON EmptySchedule = object ["success" .= False, "error" .= ("" ++ "EmptySchedule")]
    toJSON MinimumUnitsError = object ["success" .= False, "error" .= ("" ++ "MinimumUnitsError")]
    toJSON MaximumUnitsError = object ["success" .= False, "error" .= ("" ++ "MaximumUnitsError")]
    toJSON (ClassTimeCollisionError c1 c2) = object ["success" .= False, "error" .= ("ClassTimeCollisionError " ++ c1 ++ " " ++ c2)]
    toJSON (ExamTimeCollisionError c1 c2) = object ["success" .= False, "error" .= ("ExamTimeCollisionError " ++ c1 ++ " " ++ c2)]
    toJSON (FullCapacityError c1) = object ["success" .= False, "error" .= ("FullCapacityError " ++ c1)]
    toJSON BadCommand = object ["success" .= False, "error" .= ("BadCommand" ++ "")]

