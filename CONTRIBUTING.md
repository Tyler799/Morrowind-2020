# Contributing to MTE

The following is a set of guidelines for contributing to this project. Keep in mind that they are mostly guidelines, not rules so you are not obliged to follow them. But in the interest of keeping this guide project organized and easy to follow you are encouraged to do so.

## How Can I Contribute?

### Reporting Problems

You are free to report any type of problem you encounter while either following the guide or playing the game with the mod setup you will end up with after you completed the guide. These may vary from simple problems like misplaced textures or meshes to serious game crashes or save corruptions.

To report a problem you should first open a new ticket in the issue tracker on the repository page, then describe the problem in as much detail as possible. Follow these points to create a good problem report:

- **Use a clear and descriptive title** for the issue to identify the problem.
- **Describe exactly when did the problem first start occurring** in as much detail as possible. This will help us quickly identify the potential offenders and narrow down our search. 
- **Describe steps to reproduce the problem** in as much details as possible. For example if your game is experiencing a CTD after following the guide, include your mod load order as an attachment and explain when has the game last worked what have you changed since then. 
- **Explain which behavior you expected to see instead and why.**
- **Include an error or warning log if one is present**. If the problem is connected with your mod manager you will have to include an error log as an attachment. Same goes for game crashes that will often _(but not alway)_ create a log report in ```Warnings.txt``` file located in your game root directory.
- **Include screenshots** if an error log was not printed or the problem is a graphical glitch.

Your issue will then be marked with a ```problem``` label and quickly processed by a project developer. If the issue was not directly caused by our mod-list or installation instruction, or the problem was non-issue you will receive a response followed by the ticket being marked with an ```invalid``` label and closed. If a solution to your problem results in a direct change to the guide you will be informed and the ticket will be resolved _(closed)_ by an appropriate commit. Following the next release both the issue and the resolving commit will be linked in the changelog under it's respected release section.

### Suggesting Improvements

The guide is constantly evolving as we are adding new features that make it easier for you to follow the guide and stay up-to-date, as well as seeing a detail report of past changes. We are always looking for new ways to do this, and welcome any suggestions in this regard.

This includes everything that can be considered a technical improvement to the guide. Here are some examples of what suggestions fall in this category:

- **Formatting suggestions** that improve markdown readability or aesthetic appeal.
- **User support suggestions** that improve ways in which we can provide assistance in solving problems.
- **Project outreach suggestions** that help the project reach a larger audience.

Create a new issue with appropriate title and explain how would the improvement benefit the project and why you believe it should be implemented. Once the ticket has been submitted it will be marked with a ```improvement``` label and processed by a project developer. If a discussion is needed to work out the details a ```discussion``` label will be added. Once the ticket has been processed it will either be approved or rejected. In the latter case you will be notified and the issue will be closed and marked with a ```rejected``` label. As with problem tickets, if the improvement ends up being implemented you will be informed and the ticket will be resolved _(closed)_ by an appropriate commit. Following the next release both the issue and the resolving commit will be linked in the changelog under it's respected release section.

### Pull Requests

