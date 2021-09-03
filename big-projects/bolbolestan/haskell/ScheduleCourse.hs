module ScheduleCourse
( ScheduleCourse(..)
, book
, cancelBooking
, hasEmptyCapacity
) where


data ScheduleCourse = ScheduleCourse { code :: String
                                     , capacity :: Int
                                     , booked :: Int
                                     }

book :: ScheduleCourse -> ScheduleCourse
book (ScheduleCourse code capacity booked) = ScheduleCourse{code=code, capacity=capacity, booked=booked + 1}

cancelBooking :: ScheduleCourse -> ScheduleCourse
cancelBooking (ScheduleCourse code capacity booked) = ScheduleCourse{code=code, capacity=capacity, booked=booked - 1}

hasEmptyCapacity :: ScheduleCourse -> Bool
hasEmptyCapacity (ScheduleCourse _ capacity booked) = booked < capacity

