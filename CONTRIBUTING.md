# Contributing to MTE

The following is a set of guidelines for contributing to this project. Keep in mind that they are mostly guidelines, not rules so you are not obliged to follow them. But in the interest of keeping this guide project organized and easy to follow you are encouraged to do so.

## Table of Contents

[How Can I Contribute?](#how-can-i-contribute)

* [Reporting Problems](#reporting-problems)

* [Suggesting Improvements](#suggesting-improvements)

* [Direct Contributions](#direct-contributions)

[Your First Contribution](#your-first-contribution)

* [Working Environment](#working-environment)
* [Your First Commit](#your-first-commit)
* [Creating a Pull Request](#creating-a-pull-request)

[Styleguides](#styleguides)

* [Commits](#commits)

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

## Your First Contribution

### Working Environment

First off, thank you for considering getting more directly involved with the project. The following steps describes the steps you need to take to prepare your working environment. It will help you get prepared for working on the guide. If you follow them carefully they shouldn't take more then 10 minutes to complete.

- **Download and install** the latest version of [Github Desktop](https://desktop.github.com/), which will help you construct, review and push your commits from an easy to use graphic interface.
- **Fork the repository**. If you don't know what this means, read [here](https://help.github.com/en/articles/fork-a-repo) for more information. In short forking is a process of copying a repository which allows you to freely experiment with changes without affecting the original project. This is done by clicking the ```Fork``` button in the upper right corner of the repository page.
- **Clone the repository** to your local hard drive. The safest way of doing this is to use Github Desktop. Open the client and navigate to ```File -> Clone repository``` or use the key shortcut ```Ctrl + Shift + O```. Select the ```URL``` tab and enter the link to the project repository in the first field *(you can also change the local path of the cloned repository if you choose so, the default one should be ```Documents\GitHub```)* and press ```Clone```. It should take a few seconds and you will see the graphical animation displaying the cloning process. Once the cloning is complete, you can check it out and make sure everything is in order.
- **Download and install** the latest version of [Typora](https://typora.io/), a markdown reader and editor that provides a seamless experience when working with markdown files like our guide. We will be using this to... you guessed it, read and edit our guide. You can opt out of this and use a standard text editor like Notepad++, but it will be a lot more difficult for you to work with markdown files. So if you want to take that route you might as well edit the file directly from Github.

### Your First Commit

Now your working environment is all set and you are ready to get to work. Navigate to the root directory of your cloned fork and open the guide markdown file. You should notice that the icon on the file has changed. This is because Typora has associated itself with markdown files and will be your default program for opening them from now on. Feel free to configure application settings to meet your needs and check the Typora [wiki](https://support.typora.io/) for more information on what you can do with this lovely little program.

> I personally like to use the *Night Theme* because I love my eyes and starring at a shiny white piece of paper for hours at an end can be quite uncomfortable, especially at night. To change your theme navigate to ```Themes``` and pick the one you like the most.

Make the changes you planned to make and save the file. Go back to your Github Desktop and change your outline view from ```History``` to ```Changes``` where you can see the changes you made. Review the changes it the right pane and make sure your changes are good to go. Next, follow these steps:

- Make sure that the checkbox next to the guide file name is checked. This tells the client you want to include that file in your next commit.

- Enter a short and descriptive commit summary followed by an optional description if needed. Read the commit [styleguide](#commits) to see how to properly format your commit summary and description.

- Hit the ```Commit to master``` button to create your commit. Your commit should now appear on top of the stack in the ```History``` outline view and include your username and other information.

- Click the ```Push origin``` button in the large tab section just below the toolbar. This will push your commit to your remote fork on Github. You can go there now and see if it arrived.

In the next section we will create a pull request to submit our work for review before it can be accepted and merged into the project's master branch. *Note that you can and are sometimes required to make pull requests into different branches, we are just using the master branch here for your first contribution.*

### Creating a Pull Request

Now, you might be wondering what pull requests are, so here is a brief [introduction](https://help.github.com/en/articles/about-pull-requests). In short they are a simple way in which anyone can make direct contributions to the project. Creating and handling pull requests can be a daunting task at times but since we are only working with changes from a single markdown file, things should be quite straightforward. Just follow the simple steps below to create your first pull request:

- Read instructions on [creating a pull request from a fork](https://help.github.com/en/articles/creating-a-pull-request-from-a-fork) from Github help.
- Name your pull request following the commits styleguide.
- Make sure the ```Allow edits from maintainers``` checkbox is ticked to allow us to make commits on your master branch to update your pull request if there is a need for it.
- Wait for your pull request to be reviewed by a project maintainer.
- If necessary discuss the changes that need to be made for your pull request to be accepted.
- Celebrate, you have just made your first project contribution!

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