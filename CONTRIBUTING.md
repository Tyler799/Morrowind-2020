# Contributing to MTE

The following is a set of guidelines for contributing to this project. Keep in mind that they are mostly guidelines, not rules so you are not obliged to follow them. But in the interest of keeping this guide project organized and easy to follow you are encouraged to do so.

## How Can I Contribute?

### Reporting Problems

You are free to report any type of problem you encounter while either following the guide or playing the game with the mod setup you will end up with after you completed the guide. These may vary from simple problems like misplaced textures or meshes to serious game crashes or save-file corruptions.

To report a problem you should first open a new ticket in the issue tracker on the repository page, then describe the problem in as much detail as possible. Follow these points to create a good problem report:

- **Use a clear and descriptive title** for the issue to identify the problem.
- **Describe exactly when did the problem first start occurring** in as much detail as possible. This will help us quickly identify the potential offenders and narrow down our search. 
- **Describe steps to reproduce the problem** in as much details as possible. For example if your game is experiencing a CTD after following the guide, include your mod load order as an attachment and explain when has the game last worked what have you changed since then. 
- **Explain which behavior you expected to see instead and why.**
- **Include an error or warning log if one is present**. If the problem is connected with your mod manager you will have to include an error log as an attachment. Same goes for game crashes that will often _(but not alway)_ create a log report in ```Warnings.txt``` file located in your game root directory.
- **Include screenshots** if an error log was not printed or the problem is related to graphics. In the latter case you would also need to provide your MGE settings and a copy of your configuration (_ini_) file.

Your issue will then be marked with a ```problem``` label and quickly processed by a project developer. If the issue was not directly caused by our mod-list or installation instruction, or the problem was non-issue you will receive a response followed by the ticket being marked with an ```invalid``` label and closed. If a solution to your problem results in a direct change to the guide you will be informed and the ticket will be resolved _(closed)_ by an appropriate commit. Following the next release both the issue and the resolving commit will be linked in the changelog under it's respected release section.

### Suggesting Improvements

The guide is constantly evolving as we are adding new features that make it easier for you to follow the guide and stay up-to-date, as well as seeing a detail report of past changes. We are always looking for new ways to do this, and welcome any suggestions in this regard.

This includes everything that can be considered a technical improvement to the guide. Here are some examples of what suggestions fall in this category:

- **Formatting suggestions** that improve markdown readability or aesthetic appeal.
- **User support suggestions** that improve ways in which we can provide assistance in solving problems.
- **Project outreach suggestions** that help the project reach a larger audience.

Create a new issue with appropriate title and explain how would the improvement benefit the project and why you believe it should be implemented. Once the ticket has been submitted it will be marked with a ```improvement``` label and processed by a project developer. If a discussion is needed to work out the details a ```discussion``` label will be added. Once the ticket has been processed it will either be approved or rejected. In the latter case you will be notified and the issue will be closed and marked with a ```rejected``` label. As with problem tickets, if the improvement ends up being implemented you will be informed and the ticket will be resolved _(closed)_ by an appropriate commit. Following the next release both the issue and the resolving commit will be linked in the changelog under it's respected release section.

### Direct Contributions

This section is for those that would like to get directly involved in developing the guide. Reporting problems, making requests and posting questions are known as indirect contributions, which does not in any way mean they are less valuable, just that they are in an indirect way helping the development process. Creates issues still need to be resolved by commits created by contributors, and we are always looking for more contributors to get directly involved in writing and expanding the guide.

If you are interested in making direct contributions to this project read the following section.

## Styleguides

### Commits

The following style is project specific and derived from commonly accepted Git conventions. Sticking to these ensures that all commits are readable and easy to understand at first glance.

- Capitalize the first word and do **not** end the commit summary with a period.
- Write your commit message in the imperative (*Add* a cool mod" and not "*Added* a cool mod").
- Use the imperative mood ("*Format* paragraph..." not "*Formats* paragraph...").
- Use as little characters as possible for your commit summary.
- The commit body is optional but when used it should **shortly** explain the *what*, *why* and *how*. Text spanning longer then a couple of lines should be placed in an issue and referenced in the footer.

The commit footer is used to attach references to issues and other commits. Each footer line should start with designated keywords that represent the reference category followed by a semicolon:

- Use `Resolves` to automatically close an open issue.
- Use `See` to reference other commits or issues.

```
Resolves: #10
See: 50b7a68
```

Note that if the commit is in a non-default branch, the issue will remain open and it will be referenced with a tooltip. Read more about closing issues using keywords on [Github](https://help.github.com/articles/closing-issues-using-keywords/).