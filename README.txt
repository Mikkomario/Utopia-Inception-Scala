UTOPIA INCEPTION
----------------

Required Libraries
------------------
    - Utopia Flow (since version 2+)


v2  ------------------------------

Changes
-------

Package structure updated
    - handling -> handling + handling.mutable + handling.immutable

Major refactoring in all Handler classes
    - Handlers separated to generic trait + mutable and immutable implementations
    - DeepHandler to replace the old Handler implementation

Major refactoring in Handleable trait
    - Handleable separated to generic trait + mutable implementation
    - All Handleable instances are no longer Mortal, Killable as separate mutable trait

Refactored Filter traits
    - Filter is no longer a class
    - Implicit conversion from function to filter
    - !, && and || added to Filter