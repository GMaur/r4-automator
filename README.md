# Renta4 (R4) Automator

Automates the interaction with the [Renta4 broker](https://r4.com). A part of [Robot Advisor](https://github.com/RobotAdvisor) (GMaur's Investment Ideas)

As far as I know, there is no public API. (If you know about one, please send me a message / issue)

## Scope of the project

### In scope

  * Login (including two-factor authentication)
  * Read the illiquid assets from the Funds ('fondos') page.
  * Read the liquid assets from the Assets ('patrimonio') page.
  * Generating a portfolio in a common format (e.g., for further processing). Including all the parsed assets

### Out of scope 

Note: This project is open to contributions. If you want to contribute with ideas or pull requests (PRs),
feel free to go ahead.

It is currently out of scope:

  * Other types of assets 
  * Configuration (e.g., user, account, information)
  * Fiscal information (e.g., at the end of the fiscal year)
  * Anything not included in 'in scope'
  * Other currencies (than EUR)
  
### Hypotheses

  * All currencies are EURo

## Contributing

This project is open to contributions.

We're mainly interested in solving our own problems, not adding features for the sake of completion.
On the other hand, if you need to solve a problem of yours that is not currently solved by this
project, please contact us (message / issue).

### Reaching us

Please send a message (e.g., [twitter](https://twitter.com/alvarobiz)) or an [issue](https://github.com/gmaur/r4-automator/issues)
