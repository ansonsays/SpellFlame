# Spellflame

## A turn based combat game

**Instructions:**
Download SpellFlameGame.rar. Extract and run SpellFlameGame.jar.

**Description:**
*Spellflame* is a basic roguelike turn based combat game in which the player will play as a *hero*. The player will get to 
decide what kind of *spell* their hero starts with. The player can learn a maximum of three spells. The player will 
fight increasingly difficult enemy characters one at a time. Each time the player wins a fight, they will be able to 
either *learn* a new spell, or *improve* one of their existing spells before moving on to the next fight. The game ends 
when the player loses a fight. 

**Combat:**
The combat system can be compared to rock paper scissors. The player and the enemy both cast their spells at the same 
time. The game will compare the spells, check for *critical hits*, and deal damage accordingly.

**Critical Hit System:**
All spells have a type. During combat, if a spell has the *type advantage*, it will deal a critical hit. A critical hit 
means that the superior spell does *double damage*, while the inferior spell is nullified.
The types have the following cyclical relationship: 
Fire > Wind > Lightning > Earth > Water > Fire
So, for example, if the player casts a Fire spell, and the enemy casts a Wind spell, the player will land a critical; 
the enemy will take double damage, and the player will not take any.
If two spells that do not have any type relationship are cast, for example Wind and Earth, then no critical hit occurs, 
and the spells will deal their normal damage.

**Between Fights:**
After every fight, the player gets to decide whether to learn a new spell, improve an existing spell, or heal. The 
player can only choose to do one of these actions between each fight.

*UBC CPSC 210 Term Project*
