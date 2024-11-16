# Cocktail Game
<div align="center">
    <img src="/site/src/assets/icons/cocktail.svg" width="150" height="150" alt="Cocktail img">
</div>

## Overview
This project is a simple game of "Guess the Cocktail" built using Java, Spring Boot, and Vue.js. The game fetches random cocktails from a public API and prompts the user to guess the name of the cocktail based on text and contextual hints.

## Game Rules
- A random cocktail with instructions is shown to the player, the name is partially revealed (e.g "Mojito" -> "_ _ j _ t _").
- The player has 5 attempts to guess the name of the cocktail.
- If the player answers correctly, the game continues with a new random cocktail, and the score is increased by the number of attempts left.
- If the player fails to guess the cocktail after 5 attempts, the game ends.