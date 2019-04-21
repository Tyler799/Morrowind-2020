This is a changing guide. While currently considered a release canidate, it is still very open to changes and new sections.

# Morrowind 2019: Thastus Edition

This is an updated, revised and semi re-written version of Cynderal's guide (Morrowind 2017), which was an updated version of Guideanon's 2016 guide, which is apparently a revised version of the Morrowind 2015 guide.

Several developments of new mods or replacements for existing ones have come over the last couple years. Some of these are vast improvements for performance, visuals, gameplay or otherwise. Also, the previous guide had some information that was no longer relevant or true in it.

The 2017 guide can be found [here](https://pastebin.com/B8SqRJtH)
(I retain a copy on HDD of that as well as this version, should you need either. Both earlier versions appear to have been removed from pastebin completely.)

The Reddit post accompanying this release can be found [here](https://old.reddit.com/r/Morrowind/comments/9z641w/morrowind_2019_pastebin_guide_beta_release/)

Finally: YOU ARE ALSO WELCOME TO SEND SUGGESTIONS TO ME AT ANY TIME! I am *not* always in the loop regarding new mod releases, better versions of existing mods, mods that may cause stability concerns, etc. I will be glad to discuss a given mod's inclusion at your discretion.

If you need to reach me regarding this guide or otherwise, PM me at /u/Night_Thastus on Reddit. Or respond to my Reddit post. Or find me on Discord, Night Thastus #6641.

## Table of Contents

* [Before we begin](#before-we-begin)
  * [Regarding mods with archives](#regarding-mods-with-archives)
  * [Bump mapped mods](#bump-mapped-mods)
  * [Great House Fliggerty](#great-house-fliggerty)
* [Baseline Installation and Boilerplate](#baseline-installation-and-boilerplate)
* [Patches, Fixes and Otherwise](#patches-fixes-and-otherwise)
* [Official Bethesda Plugins](#official-bethesda-plugins)
* [Dialogue Mods](#dialogue-mods)
* [User Interface](#user-interface)
* [Major mods to be aware of](#major-mods-to-be-aware-of)
* [Mesh fixes and improvements](#mesh-fixes-and-improvements)
* [Nature Texture &amp; Mesh Replacers](#nature-texture--mesh-replacers)
  * [Want plants to not "bend" or "grow" towards you?](#want-plants-to-not-bend-or-grow-towards-you)
* [Weather](#weather)
* [Architecture Replacers](#architecture-replacers)
* [Miscellaneous replacers](#miscellaneous-replacers)
* [Creature replacers](#creature-replacers)
* [Heads & Bodies](#heads--bodies)
* [Clothes replacers](#clothes-replacers)
* [Weapons](#weapons)
* [Armor](#armor)
* [Animations](#animations)
* [MGE XE Setup](#mge-xe-setup)
* [Cleaning mods](#cleaning-mods)
* [Creating a multipatch](#creating-a-multipatch)
* [Updating Masters, Syncing and Reparing your Saves](#updating-masters-syncing-and-reparing-your-saves)
* [Load Order](#load-order)
* [Other mods](#other-mods)
* [Screenshots](#screenshots)
* [Contributors](#contributors)
* [License](#license)

## Before we begin

**You must have an official copy of the base game! Pirated/torrented copies will *not* work!** You need an official CD, the Steam version, or the GOG version. This isn't just heresay, this is true of every Bethesda game Morrowind and forward. All mod tools (LOOT/BOSS, Mod Organizer, Wyre Bash, MWSE/OBSE/SKSE, xEdit, etc) and even many mods themselves just don't work properly on unofficial copies of the game. The exact reasoning is fairly technical in explanation, but it's not an anti-piracy measure, it's just a fact of the game engine and how tools hook in. I'm not inherently anti-piracy, I actually think it has a lot of value for several reasons. However, for the sake of this guide, if you don't have an official copy you can expect everything to break. You likely won't even be able to start the game properly. As a veteran player of the series, **I offer exactly 0 help if you don't have an official copy**.

**You should always read the mod page or glance through the readme of any mod we're downloading**, so you'll at least be aware of what you're getting and what your options are. Mod are, above all else, personal preference. Knowledge is power!

As well: While many of the mods here may be compatible with OpenMW, many are not. Do not attempt to follow this guide with OpenMW! It is an amazing accomplishment by the community, but this guide is not attuned to it.

All installation of mods (with minor exception, mainly for tools like MGE/MCP/Mlox) will be done with Mod Organizer 2, which has built-in support for Morrowind. This is highly recommended, as it allows you to very easily disable files/folders or mods, re-order mods or plugins and otherwise. It makes maintenance of a large mod list effortless, hence its heavy adoption in the Skyrim modding scene. (Do not substitute with NMM, Vortex or otherwise. Seriously. It *will* break things.)

However: [Wrye Bash (Polemo's Fork)](https://www.nexusmods.com/morrowind/mods/45439/) is an *excellent* alternative. While a little less friendly at face value, it offers a similar ability to revert the game and mods to their base state upon revising your list. It's a fantastically made tool, and we'll be installing it anyways for some of its feature set.

**Note**: When I say "Disable" regarding a file, I'm referring to the "HIDE" feature MO2 gives. If the guide says to "Disable" a file or folder, that means "HIDE" it. However, the original guide had a lot of "DELETE" wording. I simply find/replaced that with "DISABLE". There are likely many instances where with using MO2 disabling like the original guide suggested is unnecessary - just let another mod take priority. However, I have no way of knowing immediately which disables would be pointless, so I'm leaving them in for now.

**Note**: Because we're using MO2, installation order for mods is meaningless, only their order in the left pane and plugin order in the right matter. Just remember that.

**Warning**: Do *not* enable "Automatic Archive Invalidation" when using Mod Organizer 2 for Morrowind. This is needed for a bug introducted in Oblivion. However, if used in Morrowind it *will* break things like Distant Land generation. 

### Regarding mods with archives:

Mods with archives (.BSA files) need to be added into the `Morrowind.ini` file. However, we have a tool to automate the process that can be found [here](http://download.fliggerty.com/download-58-633)
(Download down due to Fliggery site being broken)

An example for handling it manually, using my_test_mod, which has a file called `neat.BSA`:

```INI
[Archives]
Archive 0=Tribunal.bsa
Archive 1=Bloodmoon.bsa
Archive 2=neat.bsa
```

NOTE: You must edit the `Morrowind.ini` inside your Mod Organizer profile that you're playing on for it to have any effect. Assuming you didn't rename it, it's under `Morrowind/Mod Organizer 2/Profiles/Default/Morrowind.ini`.

(MO2 has a button to edit this INI directly as well)

### Bump mapped mods:
A lot of links in this guide will have bump map versions available, especially once we hit the architecture section. I don't know all the technical details of it, but bump mapping in Morrowind isn't quite like what you'd expect it to be. It's often very shiny looking, sometimes plastic-y, and it can make things look very bright depending on the reflection texture used. This is fine for metals and wet looking plants (it looks great on mushrooms, in my opinion), but can sometimes look a little weird on architecture. That means, if you ever have a choice between getting a bump map or no bump map version, the choice is 100% yours. If you do download a bump map version and decide you don't like it, you can get rid of the bumps by deleting the meshes that were included in the mod, and you can also disable the normal maps (usually they have `_nm` or `_n` at the end of their filename) if you want to save space.

### Great House Fliggerty
This site hosts a few of the mods for this list. However, due to server migration problems, downloads are broken. You can solve this by using the Wayback Machine (internet archive) for any of the given downloads. I already have links to a few of the mods, but you should be able to get the others as well.

## Baseline Installation and Boilerplate

1. Wipe any previous install, any tools, folders, etc. Everything must be clean.

2. Install the game, preferably not in Program Files for technical reasons to do with Windows permissions. If using steam, you can find information about this [here](https://support.steampowered.com/kb_article.php?ref=7418-YUBN-8129)

Make sure to run the game *at least once* to make sure it's registered on your computer properly.

If you are using the GOTY edition, no patches are necessary. If you have some ancient disc version, the Tribunal patch is [here](http://download.zenimax.com/elderscrolls/morrowind/patches/tribunal_v1.4.1313.exe) and the Bloodmoon patch is [here](http://download.zenimax.com/elderscrolls/morrowind/patches/Bloodmoon_v1.6.1820.exe).

3. Download the latest Morrowind Code patch. While its mod page can be found [here](http://www.nexusmods.com/morrowind/mods/19510/) the most updated version can often (though not always) be found [here](https://www.nexusmods.com/morrowind/mods/26348)

As of writing this guide, the Skunk Works has a *PATCH* 2.5B3, significantly more up-to-date than the mod page's 2.4, featuring many new features and improvements. (Check both first though, as it could change!)

The patch must be installed on top of the main file, in this particular case. (Again, this could change! Always check first.)

Extract it in your game directory and run it. Read through the patches, but the most important ones should be checked already. You might be interested in some of the game mechanic changing options--toggle sneak is great! For a modern install, make sure to check 'Bump/reflect map local lighting' under Graphics changes. If you want to download HD cutscene replacers, check 'Hi-def cutscene support' under Mod related features. If you want to try a sound overhaul mod later (like Morrowind Acoustic Overhaul) make sure to check 'Scripted music uninterruptible' and 'Separate axe inventory sounds'.

4. Timeslips' EXE optimizer has been removed for this guide, determined to be essentially ineffectual. (Though not harmful, from what I can tell)

5. The 4GB patch is no longer required, as it has been rolled into Morrowind Code Patch as of a recent version. However, running it against MGEXEGui.exe may improve its performance when generating distant land. The patcher can be downloaded [here](https://www.ntcore.com/4gb_patch.php)

6. Download the latest version of MGE XE. While its mod page can be found [here](https://www.nexusmods.com/morrowind/mods/41102) the development version (like MCP) exists [here](https://www.nexusmods.com/morrowind/mods/26348)
(As of writing this guide, the mod page has the newest version, Skunk Works' copy is older. Check both first though, as it could change!)

Consider patching it with the 4GB patch if distant land generation takes too long when you get to that step.

MGE XE comes with MWSE 2.0. Some mods require the alpha version (2.1) to work properly. If so, get 2.1 from [here](https://nullcascade.com/mwse/mwse-dev.zip).

If you do not have a mod that requires 2.1, there is no reason to install it - stick with what MGE XE provides. 2.1 provides support for new mods, bug fixes, and performance improvements, but is under rapid development that may cause issues. New versions of MWSE 2.1 come out daily/weekly, and it comes with a MWSE-Update program that will download any new version, if available. Visit the Morrowind Modding Discord under #MWSE for more information. To report issues, visit the Discord channel or their [GitHub project](https://github.com/MWSE/MWSE/issues).

Extract it into your install folder (the folder with Morrowind.exe in it)--not the data folder! This should result in MGEXEgui ending up in the same folder as Morrowind.exe.

Run MGEXEgui, choose your resolution and settings from the first tab (don't forget to click the Auto FOV button). I don't recommend messing with shaders at this time, and don't even bother clicking on the Distant Land tab; that's going to be one of our final steps. The In-Game tab lets you edit some .ini settings easily. Choose whatever you want. For lighting settings, use the following (from Knots' old guide):
Quadratic 2.619
Linear 1.0
Constant 0.382

7. Download mlox. While its mod page can be found [here](https://www.nexusmods.com/morrowind/mods/43001) You should actually get the latest version of it from [here](https://github.com/mlox/mlox/releases/) (They migrated away from Sourceforge some time ago)

mlox is the Morrowind equivalent of LOOT or BOSS, and it needs to go into its own folder in your Morrowind install. Mine is in `Morrowind\mlox`. Run the application and hit "update load order". You should get into the habit of doing this after you install mods that require an .esp to be activated.

mlox is, currently, highly out of date, even using version 0.62. It suggests the Morrowind Patch Project (despite those links no longer working) and has other outdated information. However, it does form a good baseline and has some information still relevant you can at least look into if it warns you.

9. Install [Wrye Mash - Polemos' Fork](https://www.nexusmods.com/morrowind/mods/45439/)

This is a powerful tool that will allow us to handle conflict resolution, cleaning saves, updating masters and otherwise.

To do this, first download the "Manual Installation Archive"

Then, extract it. The contents (`Data Files` and `Mopy`) should be put into your `Morrowind` directory, so that the Data files merges with the vanilla `Data Files` folder.

NOTE: YOU MUST RUN WRYE ONCE MANUALLY BEFORE RUNNING IT INSIDE MO2.

An installer will pop up. Point it to Morrowind's folder and Mlox if you like, and the "Mod Archives" can just be any random folder, doesn't matter which one. It'll be ignored later anyways.

10. Download [TES3CMD](https://github.com/john-moonsugar/tes3cmd/releases)

This is a powerful command-line tool that allows you to determine which mods need cleaning and clean them, reducing crashes, broken mods and otherwise.

HOWEVER: DO NOT ATTEMPT TO JUST CLEAN YOUR ENTIRE LOAD ORDER! Some mods *will* break upon being cleaned. I personally, carefully went through the mods and determined (mainly through their readme's) which plugins could not be safely cleaned. I will instruct you on which plugins to clean later in this guide.

Install it by putting it in the `Data Files` folder.

8. Download [Mod Organizer 2](https://www.nexusmods.com/skyrimspecialedition/mods/6194)
(The latest dev versions can be found on the MO2 Discord, but as of writing this, 2.1.5 is perfectly fine for Morrowind use)

Extract it so that the `Mod Organizer 2 (Archive)` folder is inside your Morrowind installation folder.

Then, go ahead and run Mod Organizer.exe. It should pop up asking you about a couple things. You'll want to choose a "PORTABLE" install (unless you're much more familiar with MO2 and know what you're doing) and when it asks you about which game you want to manage, navigate to the `Steam/steamapps/common/Morrowind` folder.

It should look like [this](https://i.imgur.com/WWbBKJZ.png)

Under the dropdown for "Morrowind" is where other executables can be put. You won't need to do this much for Morrowind. However, you'll notice that in that dropdown MGE XE has already been placed! Hurrah!

However, you'll also want to add Mlox and Wrye Bash to that list, so that they can actually see what mods you have installed. (I'm not doing to delve too far into how to use MO2 in this guide, there's a million resources on that already)

Now when you need to run MGE XE, Mlox and Wrye, do it through MO2. MGE XE may ask for elevation, that's perfectly normal. If you want to avoid that error message, you can set it to run as administrator on your own.

Congratulations, you've completed the baseline installation for Morrowind!

**Before you continue:** I would highly recommend launching the game for a second time. You knew it worked vanilla, now you should test and make sure it works with all this boilerplate work you've done. If it does, you can go on to installing mods.

## Patches, Fixes and Otherwise

A note before we start installing mods:

MO2 allows you to use the "Download with Manager" option on the nexus pages that have it. Feel free to use this to save time. However, MO2 of course can install archives too using the second to leftmost top button with a CD on it.

As you begin installing mods, I recommend that you right click them and hit "Ignore Update" if the version number shows up in red. This will happen due to some mod authors having poorly layed out versioning for their mods, making a mod show up as "out of date" despite you just installing it. This isn't harmful in any way, but if you do this *now* then when you hit "Check all for updates" in the future you'll actually have a pretty good idea if any of your Nexus mods have updates available. Very useful!

1. You may be familiar with projects like the Unofficial Patches for Oblivion and Skyrim. Morrowind *sort of* has its own versions of these in the form of two options: the Morrowind Patch Project and the Patch for Purists. The MPP makes a lot of balance-related changes some players feel is untrue to the original experience. Thus, currently most players recommend the Patch for Purists which you can get [here](https://www.nexusmods.com/morrowind/mods/45096/)

(Note: Mlox, due to being old and outdated, will complain because you don't have the Patch Project installed. If this bothers you, you can remove this message by going into Mlox_base.txt, and removing the section for the Patch Project. It will not longer message you about it, which is nice.)

2. Go ahead and get the following mods:

* [Texture Fix 2.0](http://mw.modhistory.com/download-90-10353) - Repairs landscape seams
* [Texture Fix Extended](https://www.nexusmods.com/morrowind/mods/46018)
* [Texture Fix - Bloodmoon 1.1](http://mw.modhistory.com/download-90-10388) - Repairs landscape seams on Solstheim. (You'll need to disable this mod if you choose to install Solstheim - Tomb of the Snow Prince later in this guide)
* [Bloated Caves](http://www.nexusmods.com/morrowind/mods/43141/) - Adds bloatspores (a plant) to Morrowind. Bloatspores were included in the game files but not placed in the world.

One of the following:
* [Delayed Dark Brotherhood Attack Add-On 2.0](http://mw.modhistory.com/download-90-7300) - Delays the Tribunal main quest from staring until certain "immersive" requirements are met. Does NOT work with Rebirth!
* [Dark Brotherhood Delay - Player's Choice](https://www.nexusmods.com/morrowind/mods/46533) - Delays the Tribunal main quest from starting until either a level condition is met, or you've completed the main quest depending on what option you choose. Does work with Rebirth. 

## Official Bethesda Plugins

Bethesda made a number of official plug-ins for Morrowind. Unfortunately, they have some problems. While you can download their vanilla versions individually, it's recommended to instead download a patch version of all of them. (You can easily disable the plugins you dislike in MO2)

There are two main options for fixed version of these addons:

- [Unofficial Morrowind Official Plugins Patched](https://www.nexusmods.com/morrowind/mods/43931) - This is the most up-to-date option that comes with compatibility patches for various big mods. You need the main file and optionally "Merged and Compatibility versions for UMOPP" as well. I personally recommend the optional compatibility merged file as it gives you wider compatibility with other mods you might use in the future. If you do use it, choose the "`UMOPP Compatibility Merged` in the BAIN installer. Remember to disable the main package plugins if you are using the merged version. If for some reason you don't want to use specific addons, you shouldn't use the merged version and just load the *Compatibility ESPs* after the main package.

- [WHReaper's Morrowind Official Plugins](http://download.fliggerty.com/download-13-1079) ([Mirror](https://web.archive.org/web/*/http://download.fliggerty.com/file.php?id=1079)) - Some of the folders have optional components, usually with higher res textures, glow maps, or normal maps. Feel free to install all of the optional folders. To install, you'll need to download the archive, merge any optional folders into their parent mod folder, and then pack them into .7Zip or .zip files, which you then can install via MO2 as normal)

You can read about the official add-ons [here](http://www.uesp.net/wiki/Morrowind:Official_Plug-ins)

## Dialogue Mods

1. First and foremost you should get the [LGNPC](http://lgnpc.org/downloads)

Download the LGNPC bundle. You should install all of the mods. A few notes:

* If you download the "all in one" it is indeed not properly archived for installation. It also includes old versions of several of the mods. You'll need to extract it, then delete the archives inside for the older copies, then install each individually. My recommendation is just to skip the "all in one" and download the latest versions of each individually.
* If you want to avoid wereguars, skip LGNPC Pelagiad. It's one of their earliest mods and it really shows.
* Make sure to read LGNPC Soul Sickness Patch's readme. It's optional, so decide on your own if you want it or not.
* The plugin "LGNPC_SecretMasters_MCA5.esp" is only relevant if you have Morrowind Comes Alive. If you do not, make sure to right click the "Secret masters" mod and turn this ESP from "Available ESPs" to "Optional ESPs", or just make it hidden.
* The Soul Sickness patch comes bundled with Pax Redoran, and does not need to be installed seperately.

2. Get the Less Generic modules. These give the main quests of Morrowind, Tribunal and Bloodmoon the LGNPC treatment and are fantastic. It does tend to make some of the quests a little trickier, though.

* [Less Generic Nerevarine](http://www.nexusmods.com/morrowind/mods/23335/)
* [Less Generic Tribunal Restored](https://www.nexusmods.com/morrowind/mods/44819) (This is a fixed version of Less Generic Tribunal that doesn't need several patches)
* [Less Generic Bloodmoon](http://www.nexusmods.com/morrowind/mods/23346/) - Comes with one patch for users of Thirsk Expanded, and one patch for users of **both** Thirsk Expanded and Children of Morrowind, and finally the main plugin. Make sure to enable the main plugin, and one of the two patches if appropriate. (For reference, this guide installs neither TE nor CoM)

3. A few extra mods to add even more to the dialogue of the game:
* [Django's Dialogue](http://www.nexusmods.com/morrowind/mods/37328/)
* [Nastier Camonna Tong](http://www.nexusmods.com/morrowind/mods/22601/)
* [What Thieves Guild?](http://mw.modhistory.com/download-87-13858)

## User Interface

- [Ultimate Icon Replacer](http://mw.modhistory.com/download-56-6673). This will replaces all Morrowind object/inventory icons with better icons. Mods you install further down the line will take priority over some of these.

- [JiFFY Morrowind UI Revamped](http://www.nexusmods.com/morrowind/mods/43922/?). This UI mod was chosen in particular because of how much it revamps, and it looks nice to boot. Choose between Dark or Classic, pick your crosshair and choose any size options. I went with 50% smaller crosshair and 25% smaller cursor, but you can and should try all the sizes in game to see which you prefer. Optionally for those avid readers, this mod includes the mod "Scroll Daedric Alphabet". This mod gives you a key to read the Daedric characters on spell scrolls. Lastly, don't forget to make the necessary changes to your .ini.

- [Spell Scroll Text Overhaul](https://www.nexusmods.com/morrowind/mods/42936) gives unique Daedric text to all spell scrolls, making them far more interesting to read if you added Scroll Daedric Alphabet in the JiffyUI install.

- [UI Expansion](https://www.nexusmods.com/morrowind/mods/46071) - Expands UI functionality with searching, filtering, and more visual feedback.	Has compact and expanded views. A very nice mod. 

### Dialogue Font

Get your dialogue font mods of choice:

* Start with [Better Dialogue fonts](http://www.nexusmods.com/morrowind/mods/36873/), which makes the standard Morrowind font (Magic Cards) higher resolution. Consider the mods below afterwards if you wish.

* [Bigger Vanilla Font](http://www.nexusmods.com/morrowind/mods/42420/) - Optional. Use if you'd like a larger font. Requires Better Dialogue Font and 'Unrestrict menu size' option from MCP.

* [MORRA BUF - MORe ReadAble Bigger UI Fonts](http://www.nexusmods.com/morrowind/mods/42934/) - This is an alternative to Better Dialogue Font if you dislike the "Magic Cards" font. Take note of the .ini changes you'll need to do.

### HD Video Screen Replacers

Get any or all of these if you like:

* [HD Intro Cinematic - English](http://www.nexusmods.com/morrowind/mods/39329/) - Make sure to pick the right one for your resolution, and only get this if you picked the proper option in MCP. To install, you need to rename the movie file to `mw_intro.bik` after installing it via MO2.
* [Morrowind Bethesda Logo HD Makeover](http://www.nexusmods.com/morrowind/mods/42352/) - If you have 'skip intro movie' turned on in MGE XE, there's no point to downloading this. In order to install, you'll need to make a folder called `Video` and drop the `bethesda logo.bik` file into it.

### Splash Screen Replacers

Before installing any of these it might be a good idea to move all of vanilla splash screens located in ```Data Files/Splash``` in a new mod and then disable it. Just create the directory structure just mentioned, drag and drop all vanilla splash screens in there, package it with a name like "Vanilla Splash Screens" and add it through MO. This will ensure that only the custom splash screens get shown in the game, and if you ever want the vanilla ones back you can just enable the new mod.

* [Fresco Splash Screens](https://www.nexusmods.com/morrowind/mods/45680) - This is a set of 14 splash screens showing off Tyddy's frescoes. They are very vanilla friendly and quite minimalistic. Editors personal choice.

* [HD Concept-art Splash Screen and Main Menu](http://www.nexusmods.com/morrowind/mods/43081/) - Skip this if you absolutely can't stand the Russian writing on the splash screens, but the screens look amazing enough that you should overlook it. To install, you'll just need to set the "Data Files" section as the Data directory when installing via MO2, then it works fine.
* [Morrowind Screens Redone](https://www.nexusmods.com/morrowind/mods/46259) - An English alternative to the above, based on the same concept art. Looks a bit different, compare their images and decide yourself.

If you aren't a fan of the suggested main menu/splash screen replacer, you have several other options to choose from:

* [2015 Main Menu Background Replacers](http://www.nexusmods.com/morrowind/mods/43923/) - These look absolutely gorgeous, and in most cases would be the ones I would recommend first when it comes to the main menu. Only choose one.
* [Animated Main Menu for Morrowind](http://www.nexusmods.com/morrowind/mods/43341/) - This is a really pretty main menu. The downside is that it's a 300 mb file. I personally don't want to waste that much space on the main menu, but if you don't care about that this may be a good option.
* [Concept Art Based Loading Screens](http://www.nexusmods.com/morrowind/mods/43603/) - For splash screens, this would be my personal recommendation. If you like the look of the primary replacer but can't stand the Russian writing, you may enjoy this.
* [Morrowloading](http://www.nexusmods.com/morrowind/mods/44364/) - If you want splash screens with tidbits of information, like in Oblivion and Skyrim. Note that the screenshots used by this mod differ occasionally from how your game will look following this guide.

## Major mods to be aware of

The following mods are optional to install, but have compatibility patches listed in the mesh/texture replacers we'll soon be downloading, so I'm listing them here. Read up on them and decide if you want to use them or not.

* [Tamriel Data](http://www.nexusmods.com/morrowind/mods/44537/?) - This is a requirement for the below mod. Download the HD version.
* [Tamriel Rebuilt](http://www.nexusmods.com/morrowind/mods/42145/?) - One of the biggest mods in Morrowind and still a work in progress, but it adds a huge chunk of playable landscape (mainland Morrowind). It shouldn't be incompatible with anything listed here, but you should keep an eye out for things with TR compatibility if you choose to install it.
* [Graphic Herbalism](http://www.nexusmods.com/morrowind/mods/43140/?) - By default, plants in Morrowind act as containers: when you activate them, the container interface opens and you can remove any ingredients that might be present 'inside' the plant. Graphic Herbalism is a rather old mod that makes plants act more like plants; activating the plant picks it, you automatically get the ingredients, and the plant changes in the world to show it has been picked. Think Skyrim plants. If you want to use Graphic Herbalism, you'll need to keep compatibility in mind when we download plant replacers, otherwise it might be a little jarring when your smooth plant mesh gets replaced with an ugly vanilla mesh and texture.

If using both Tamriel Rebuilt and Graphic Herbalism, you'll want the Tamriel Data patch from [here](http://www.nexusmods.com/morrowind/mods/43829/)

* [Glow in the Dahrk](https://www.nexusmods.com/morrowind/mods/45886/) is essentially a replacement for WinDoors Glow. Highly recommended.
(I personally chose the High-res and rays options, and left the rest off)

However: If you want to use it you *must* have MWSE alpha 2.1. It will not work with 2.0!

## Mesh fixes and improvements

* [Better Meshes plus Optimization](http://www.nexusmods.com/morrowind/mods/38170/?)
* [Dwemer Mesh Improvement](http://www.nexusmods.com/morrowind/mods/43101/?)
* [Mesh Improvements Optimized](http://download.fliggerty.com/download-56-1088)
* [RR Mod Series - Better Meshes](http://www.nexusmods.com/morrowind/mods/43266/?) - Get the RR - Better Meshes V1.2 file and the Bittercup fix. Before installing, disable `meshes/m/misc_com_pillow_01`.
* [MOAR Mesh Replacers](http://www.nexusmods.com/morrowind/mods/44057/?)
* [Morrowind Optimization Patch](https://www.nexusmods.com/morrowind/mods/45384) - Improves performance by optimizing meshes.
* [Project Atlas](https://www.nexusmods.com/morrowind/mods/45399) - Improves performance by merging meshes into a single shape, reducing drawcalls significantly for the same visual quality. Download all files.

## Nature Texture & Mesh Replacers

This is by far going to be the most complicated part of the guide so hold on to your butts.

* [Morrowind Visual Pack](http://mw.modhistory.com/download-56-6990) - This is our base mod. It replaces a lot of stuff that still isn't touched by modern mods, mostly because they're not really used often enough to think about replacing. Most of the textures will be overwritten.

### Bitter Coast Region

* [Connary's Bitter Coast](http://www.fullrest.ru/files/connarysbittercoast)
* [Apel's Bitter Coast Retexture](http://www.nexusmods.com/morrowind/mods/42661/?)

Feel free to start the game and check out Seyda Neen if you'd like, but we're not done and the Bitter Coast landscape will be changing more later as we add tree, plant and new landscape textures.

### Connary Packs

While we're at it, lets get a few more Connary packs. Some of this stuff will end up overwritten later; that's fine.

* [Connary's Grazelands](http://www.fullrest.ru/files/connarysgrazelands)
* [West Gash](http://www.fullrest.ru/files/connaryswestgash)
* [Webs](http://www.fullrest.ru/files/connaryswebs)
* [Caves](http://www.fullrest.ru/files/connaryscaves)

### Rocks

Time for rocks. Download the following:

* [Correct UV rocks v1.0](http://mw.modhistory.com/download-56-12003)
* [WIP Smooth Correct UV Rocks](http://forums.bethsoft.com/topic/1514660-alternatives-to-on-the-rocks/?p=23908143)
* [Ore Rock Retexture ORR](http://mw.modhistory.com/download-56-12942)
* [correctUV Diverse Ore veins v1.0](http://mw.modhistory.com/download-56-13484)

If you're using Graphic Herbalism and it's Extra Mining module, get this [patch](http://www.nexusmods.com/morrowind/mods/43197/?). You will need to disable Graphic Herbalism - EXTRA mod in your left pane completely, or if you merged it, just disable it's ESP.

### Waterfalls

Test each waterfall mod quickly by starting the game, pressing `\`` to go into the console, typing `COC "Vivec, Puzzle Canal, Level 1"`, hitting enter, and leaving through the grate ahead of you.
* [Better Waterfalls](https://www.nexusmods.com/morrowind/mods/45424) - Best looking ones out there. Editors personal choice. Be sure to grab the [Waterfalls Tweaks](https://www.nexusmods.com/morrowind/mods/46271) for better compatibility.
* [Waterfalls Bump mapped](http://www.nexusmods.com/morrowind/mods/42405/?) - Author's personal choice.
* [Dongle's Waterpack Bumpmapped](http://www.nexusmods.com/morrowind/mods/42317/?) - Some may like how these look.

### Fire and Smoke

* [Apel's Fire Retexture](http://www.nexusmods.com/morrowind/mods/42554/?)
* [Vurt's Lava and Smoke](http://www.nexusmods.com/morrowind/mods/28519/?) - Just choose the "SMOKE" option in the BAIN installer. (Though you could choose the others, they'll be lower priority than lava mods we'll be installing later and thus shouldn't come into effect)

### Trees

#### Bitter Coast Trees

* [Vurt's Bitter Coast Trees II](http://www.nexusmods.com/morrowind/mods/37489/?)
* [Vurt's Bitter Coast Trees II - Remastered and Optimized](https://www.nexusmods.com/morrowind/mods/46418) - Fixes a good deal of Vurt's Bitter Coast Trees II and improves their performance. You no longer need the BC Trees Collision fix or the Bitter Coast folder of Vurt's Tree Fix by Greatness7. This is a patch so you will need to place it just bellow the original mod in your load order.

#### Grazeland Trees

* [Vurt's Grazelands Trees I](http://www.nexusmods.com/morrowind/mods/35368/?)

* [Vurt's Grazeland Trees II](http://www.nexusmods.com/morrowind/mods/37038/?) 

Pick the one you like best. I prefer the first option, and I use the palms free version. Make sure to move **one** of the ESPs out of the Optional section using MO2, depending on which you want.

#### Ascadian Isles Trees

Use only one of the following two mods:

* [Remiros' Ascadian Isles Trees 2](https://www.nexusmods.com/morrowind/mods/45779) - If you care about optimization, this should be your preferred choice of trees for this region. They are not only better for performance, but also more vanilla friendly.
* [Vurt's Ascadian Isles Trees Replacer II](http://www.nexusmods.com/morrowind/mods/37249/?) - These are not optimized and will have a medium impact on your performance in Ascadian Isles. They also require patching to correct some mesh problems. However some might prefer them over Remiros trees. Make sure to get the newer file (10a).

If you've chosen to go for the Vurt option you'll need to install this fix: 

* [Vurt's Trees Fix by Greatness7](https://www.dropbox.com/s/rwtjc7stx1gbeje/VurtsTreesFix.7z?dl=0) - Install the meshes from the `Ascadian Isles` folder only. Leafy West Gash can be disabled if you're following this guide as we used different trees. Only use the Grazelands folder if you're using Grazelands II. You'll need to re-pack these, as they aren't correctly archived. In each folder, all the .NIF files should be in a `Meshes/f` folder. Make sure to set them up that way and re-pack them, then install as normal.

#### Ashland Trees

* [Dahrk Mods by Melchior](http://www.nexusmods.com/morrowind/mods/43528/?) - Specifically, Vurt's Ashtrees - Shorter. 

#### Mournhold Trees

The following two are the recommended tree replacers for the Mournhold region. They are well optimized and quite beautiful. I would recommend merging these two mods into one called "Vurt's Mournhold Trees". If you noticed that they contain some duplicate textures that's fine, just overwrite them.

* [Vurt's Mournhold Trees II](http://www.nexusmods.com/morrowind/mods/35400/?)

* [Vurt Mournhold Dead Trees](https://www.nexusmods.com/morrowind/mods/45770)

Alternatively you can choose CptJoker's Flora Redux:

* [Mournhold Flora Redux](https://www.nexusmods.com/morrowind/mods/45805) - Highly-detailed trees and grass textures for Mournhold. These should be very performance heavy as they have a whopping 10 times more detail then vanilla trees, use them only if your rig can handle it. Get the "MFR - Trees" or "MFR - Alternative" file.

#### Solstheim Trees

* [Vurt's Solstheim Tree Replacer II](http://www.nexusmods.com/morrowind/mods/37856/?) - Also comes with a "Fixed Collision" patch. Will also need to be re-packed so all .NIF files are in a `/Meshes/f` folder. Be sure to install the following fix as well.
* [Vurt's Solstheim Tree Replacer II Fix](https://www.nexusmods.com/morrowind/mods/45941) - Fixes `Meshes/f/Flora_tree_BM_snow_01.nif`

#### West Gash Trees

For the West Gash trees, you have two options. Vurt's look nicer, in my opinion, but Vanilla-friendly West Gash is MUCH more... well, vanilla friendly. Only use ONE of the mods offered below.

- [Vanilla-friendly West Gash Tree Replacer](http://www.nexusmods.com/morrowind/mods/44173/?) - As with the Ascadian Isles trees, this should be your preferred choice of trees for this region due to problems Vurt's trees tend to have. I recommend using the darker leaf texture found in the `extras` folder.

* [Vurts Leafy West Gash II](http://www.nexusmods.com/morrowind/mods/37400/?) - Make sure to get the newer file. Do NOT download the bridge rope textures.

### Plants

* [Underwater Static Replacer](http://mw.modhistory.com/download-56-11998)
* [Better Barnacles](http://www.nexusmods.com/morrowind/mods/43605/?)
* [Lush Ascadian Isles](http://www.nexusmods.com/morrowind/mods/43872/?)
* [Plant life retexture](http://www.nexusmods.com/morrowind/mods/37947/?)
* [Bloatspore retexture](http://www.nexusmods.com/morrowind/mods/42384/?) - We already have a better bloatspore mesh, so just get the 'simple retexture' file. Unless you REALLY want bump/glow mapped bloatspores for some reason, but you'll have a less smooth bloatspore as a result.
* [flora_bush_01 replacer](http://www.nexusmods.com/morrowind/mods/42941/?) - Get the 1.3 file, and I highly recommend grabbing the 1k texture optional file because 2k textures for a bush is a little overkill. Make sure to look through your options. I used the flowerless version of the mesh with browner bark. If you do decide to change anything, make sure to follow the instructions closely and rename the files properly.
* [Trama Bump mapped](http://www.nexusmods.com/morrowind/mods/43015/?) - Make sure to get the Graphic Herbalism compatibility patch if you need it.
* [Hackle-lo Fixed](http://www.nexusmods.com/morrowind/mods/42784/?) - The "EXTRAS" option in the BAIN installer is the Graphic Herbalism patch.
* [Comberry Bush and Ingredient Replacer](http://www.nexusmods.com/morrowind/mods/42586/?) - The EXTRAS option contains a patch for Graphic Herbalism. Additionally, there's another option which includes vanilla style textures. I recommend it.
* [Improved Kwama Eggs and Egg Sacs](http://www.nexusmods.com/morrowind/mods/43555/?) - Technically not a plant, but they work the same way plants do. The install is a little complicated if you're doing it manually. If you want the full package (bump maps + animation) get `00` + `01` + `03` (and `05` if you use Graphic Herbalism). If you don't want bump maps but do want the animation, get `00` + `02` (and `04` for GH). For a no frills retexture, just get folder `00` (and `04` for GH).
* [Fire Fern Plant and Ingredient Retexture](http://www.nexusmods.com/morrowind/mods/43568/?) - Get the GH patch if you need it.

* [Better flora](https://mega.nz/#!HwpRRRZQ!IKYOhTxBrfEATUXGltYNsESBZs_Y-CZRQ4zq2bM8duo)  - We're just after the meshes from this mod, but the original had collisions for all the flower meshes. I've fixed that in this version. You do need to disable four files from `meshes\o`), and those are the three kreshweed .nifs and the marshmerrow .nif. If you don't like spinning flowers, you might want to disable the gold kanet meshes as well, as the flowers will rotate when you move your camera. Included in the extras folder are Graphic Herbalism meshes. If you want to use them, you'll either need to make them a seperate mod, or just pull them into the `meshes` folder with the rest of the mod and re-pack it.
Original mod this was based on can be found [here](http://www.nexusmods.com/morrowind/mods/43288/) (Do not download this)

* [Ascadian Isles Plants](http://www.nexusmods.com/morrowind/mods/36810/?) - If you'd like, install these. I personally don't like the way they look in-game, I think they stick out too much. You might want to skip the comberry bush meshes and textures if you prefer Pherim's (which we already installed). Your choice. If you'd like to use the flowers but want to skip the bush, simply disable the following files before copying into your installation:

```
Meshes\f\flora_comberry_01
Meshes\Gherb\Comberry_01_P
Meshes\n\ingred_comberry_01
```

If you aren't using Graphic Herbalism, it's actually safe to disable the entire `Meshes\Gherb` folder.

* [EKM Corkbulb Retexture](http://www.nexusmods.com/morrowind/mods/43809/?)
* [EKM Vanilla-Based Ash Grasses](http://www.nexusmods.com/morrowind/mods/43836/?)

### Solstheim Textures

* [Solstheim - Tomb of the Snow Prince v1.3](http://abitoftaste.altervista.org/morrowind/sopp/)

Download either `wl_SolstheimOverhaul_v1.3.7z` or `wl_SolstheimOverhaul_v1.3 - BSA version.7z`. If using the BSA version, make sure to append it to the list of BSAs as stated earlier in the guide. This is an ABOT-updated and much-fixed version of TOTSP. 

The only real difference between these two is:

* BSAs always lose to loose files in terms of conflict resolution in MO2. If you're concerned that TOTSP might have some files over-written by another mod in a way that could cause issues, you'll want the loose files instead
* BSAs are generally read faster by Morrowind, and are also more compact. This can help in load times. 

This mod is actually a massive overhaul of Solstheim. If you're not interested in a massive overhaul, you should download it for the meshes and textures. Just don't activate the .esm. You can safely disable the following folders: (However, they will simply not be loaded, so it is not necessary to disable them)

```
Icons\WL
Meshes\WL
Textures\WL
```

Some players report that this mod is an FPS killer, has broken quests and might otherwise be a slight pain. However, it is being redone. Should that happen, I'll replace it in this list. Remember, it's not mandatory.

For Graphic Herbalism users, you'll need the relevantly-named patch from [here](https://www.nexusmods.com/morrowind/mods/43829)

REMINDER: IF YOU ARE USING THIS OVERHAUL (TOTSP) YOU MUST REMOVE "Texture Fix: Bloodmoon" (As it is made for a vanilla landscape)

### Landscape Textures

* [Tyddy's Landscape Textures](http://www.fullrest.ru/files/tyd_landscape-texture_compilation_1/files) OR [Vanilla Land](https://www.nexusmods.com/morrowind/mods/45953)

Vanilla land is generally thought of as better looking and more compatible visually with vanilla style textures.

Alternatively, choose both, and put Vanilla Land after. (My personal choice, but it is more downloading for you to do)

There's a lot of overlap between these two mods, but 50 textures or so from Tyddy's aren't covered by Vanilla Land. Thus, by loading both and putting Vanilla Land After after, we get the vanilla land textures while still keeping anything from Tyddy's that VL doesn't cover.

Disable the following after installing, we have better from Lush Ascadian Isles:

```
tx_ai_clover_02
tx_ai_dirtpatch_01
tx_ai_dirtroad_01
tx_ai_grass_01
tx_ai_grass_dirt_01
tx_ai_mudflats_01
tx_grass_01
tx_grass_rocky_01
tx_rock_brown_02
tx_rockybadlands_01
tx_rockyscrub_01
```

Note that some of these may not exist in the files to disable. This isn't a problem, jut disable all of the ones that are listed and can be found. And no, simply loading this mod and STOSP before Lush isn't quite good enough, there are other files that lush would take priority for that we don't want)

### Want plants to not "bend" or "grow" towards you?

I hate this effect. It's in Ozzy's grass, and much more minor in Vurt's grass. Turns out, it's very easy to fix.

In `XE Common.fx` replace

    return saturate(0.02 * h) * (harmonics * displace + stomp);

with

    return saturate(0.02 * h) * (harmonics * displace);

Ta-da! Effect removed!

## Weather

### Skies

* [Skies .IV Resource Pack](https://www.nexusmods.com/morrowind/mods/43311/?tab=files) - Disable/Hide `ashcloud.nif` and `raindrop.nif`.
* [Alternate Skies](http://www.nexusmods.com/morrowind/mods/42629/?) - Make sure to download the second main file 'Alternate Skies'. The first one is just a single night sky texture, not the whole mod. 
* [New Starfields](https://www.nexusmods.com/morrowind/mods/43246?) - This mod is improperly packaged. Instead of a BAIN installer with options for each version, it requires a bit more leg work. First, download it and check through the provided screenshots, or look at them on the mod page. You'll note (confusingly) that sometimes multiple versions will have only a single screenshot. Once you've decided on one, re-name that file (for example `tx_stars_06.dds`), to `tx_stars.dds`. Then, create a `Textures` folder, place it inside, and delete everything aside from that `Textures` folder. Then, re-pack and install as you would normally. (Sorry for this, I know it's annoying)

### Moons

- If you want a lore friendly moon re-texture take a look at [Lorkhan's Lunar Legacy](https://www.nexusmods.com/morrowind/mods/45718).
- If you want moons that look like they once contained life, try [Dying Worlds](http://www.nexusmods.com/morrowind/mods/43023/?).
- If you want moons that more closely resemble their appearances in the later games, download Skies v3 (Legacy) from the Skies .IV [mod page](https://www.nexusmods.com/morrowind/mods/43311/?tab=files) and install the moon textures from there instead (you only need to copy the content of the Moons folder).

### Tweaks

You might notice your clouds moving by far too quickly. Let's fix that. First, make a backup of your `Morrowind.ini`. (This can be found under `Morrowind/Mod Organizer 2/Profiles/Default`, or whatever you named your profile if you renamed it)

~~Now you need to edit your ini file by carefully following the instructions provided in the "Weather settings.txt" that comes with Alternate Skies.~~

Since Morrowind stores the settings related to weather and nighttime lighting levels in MORROWIND.INI which cannot be modified by simply installing a mod in the Data Files directory. You should download Vladimir Kraus' excellent MiNi to help make the necessary INI modifications:
- [Download](https://cdn.discordapp.com/attachments/533267121384325120/564203414590980097/Alternate_Skies_Settings_Automated_.7z) and run MiNi.exe
- Ensure that the path displayed at the bottom of the screen points to your Morrowind directory (eg. 'C:\Program Files\Morrowind')
- Select the Green down arrow to backup your current MORROWIND.INI file
- Select Alternate Skies Settings
- Select the APPLY icon (pencil writing on paper)
- And exit the utility

Alternatively you can grab the "Weather settings.txt" file that comes with Alternate Skies, then copy/paste the weather sections over your own weather settings in your .ini.

If you ONLY want the cloud speed .ini adjustments required for Skies and not the weather color changes, change these lines in your .ini:

```ini
[Weather Clear]
Cloud Speed=0.50

[Weather Cloudy]
Cloud Speed=0.75

[Weather Foggy]
Cloud Speed=1.25

[Weather Overcast]
Cloud Speed=0.50

[Weather Rain]
Cloud Speed=0.75

[Weather Thunderstorm]
Cloud Speed=1

[Weather Ashstorm]
Cloud Speed=3

[Weather Blight]
Cloud Speed=5

[Weather Snow]
Cloud Speed=1.5

[Weather Blizzard]
Cloud Speed=3.5
```

Keep in mind the Snow/Blizzard sections might be at the end of your .ini and not with the rest of the weather sections, as they were added by Bloodmoon.

And with that, we're done with the nature section of the guide. If you'd like, generate distant land (if you plan to use it) and run around in-game to check things out. We'll be generating MGE XE grass in a later step, so don't worry if you still find the landscape bare.

>Q: Why didn't you just disable the meshes from Better flora yourself if you made the fixed version?
>
>A: Just in case anyone prefers the Better flora kreshweed and marshmerrow to Apel's versions. I wanted it to be a complete archive.

>Q: Why didn't you include On the Rocks?
>
>A: Despite the fact that On the Rocks has much more detailed rock meshes, they unfortunately change the shape/size from vanilla so drastically that they sometimes block entrances to caves and tombs. This is especially prevalent in landmass mods (like Tamriel Rebuilt) or quest mods that use rocks. The same can be said for Apel's Asura Coast rock meshes, which is why we overwrote them. This CAN be fixed by going into the console (\` key), clicking on the rock in question and typing disable, then hitting enter, but a workaround like that shouldn't be required to play the game. If you want to use OtR anyway, do the following:

1. Skip out on downloading Correct UV Rocks and WIP Smooth Correct UV Rocks. [If you've already installed everything, that's fine, but you should go back and reinstall the `Meshes` folder from Apel's Asura's Coast mod before continuing.]
2. Download ONLY the Ashlands, Molag Amur and Red Mountain modules from [here](http://mw.modhistory.com/download-44-14107).
3. Download everything except Azura's Coast and Shores from [here](http://www.nexusmods.com/morrowind/mods/43075/?) and install it.

That's it, you're done. Now you have nicer (and bigger) rocks.

>Q: Why did you suggest On the Rocks Optimised instead of regular On the Rocks?
>
>A: Because optimization. Less polys means higher FPS and Morrowind already runs pretty poorly if you completely mod it. Every little bit helps. If you really want to use the full versions, go ahead. Skip step 3 and download every module except Azura's Coast and Shores from the link on step 2. Apel's Asura's Coast mod replaces the Azura's Coast and Shores rocks, so those modules are unnecessary.

>Q: Why use Tyddy's landscape textures instead of OtR textures?
>
>A: Personal preference. Tyddy's textures are a lot more vanilla friendly than the ones used by Taddeus, and there are times when different region rocks will be right next to each other and look very out of place with OtR textures. Feel free to use Taddeus' if you want, though, it's your install.

>Q: Why didn't you include Caverns Bump mapped by Lougian?
>
>A: I originally planned to, but the textures included in it are a pretty vast departure from vanilla, which is important to me. The only real alternative was Connary's, which come pretty close to vanilla. But the deciding factor was actually the comments section on Nexus, which pointed out a lot of issues with the meshes, and Lougian himself commenting that he really needed to update the mod. It hasn't been updated in over two years. If you want to use it despite the problems with it, feel free. You can get it [here](http://www.nexusmods.com/morrowind/mods/42412/?).

>Q: What if I don't want to use Vurt's trees?
>
>A: Unfortunately you don't have a lot of options... You can get correct [UV Trees](http://mw.modhistory.com/download-35-5808) to make the textures look better. You can use the billboard trees from [Better flora](http://www.nexusmods.com/morrowind/mods/43288/?), but keep in mind you'll need to replace the meshes for distant land generation, and then switch them back afterwards. SWG has a similar, though older replacer for [the Grazelands](http://www.nexusmods.com/morrowind/mods/24537/?) but it doesn't have a distant land compatibility patch, so you'll have no leaves.

## Architecture Replacers

Hopefully this will be slightly less complicated than the nature section. Something to keep in mind: if a section mentions bump mapping, you CAN safely ignore/skip the bump map mod and use only the textures listed if you want to. Bump mapping doesn't always look great in Morrowind. If, as you're going along, you actually prefer the look of one of the bump-mapped mods instead of my suggestion (meaning Lougian's textures instead of Tyddy's, most of the time), feel free to use the bump map textures instead and skip my recommendation. The ones I picked are closer to vanilla Morrowind, but that doesn't mean it's to everyone's taste.

1. Windows. No longer required, Glow in the Dahrk handles this great now. You can look at getting texture replacements, but look at them beforehand first and ask around a bit.

2. Imperial architecture and shacks.
* [Bump-mapped AOFs Imperial Housing](http://www.nexusmods.com/morrowind/mods/42407/?) - Install options 1 and 3 in the BAIN installer.
* [Bump-mapped Imperial Housing Fixed Filter Mode](https://www.dropbox.com/s/sjsxdkuy46d136k/Bump-mapped_AOFs_Imperial_Housing-fixed_Filter_Mode.7z) - Fixes issues with the meshes from the last step. Again, skip this if you don't want bump maps. Only choose the first option in the BAIN installer. (Ignore WinDoors glow option)
* [Imperial Houses and Forts Retexture](http://www.nexusmods.com/morrowind/mods/43940/?) - This is the actual texture pack we'll be using. HQ is 2k, MQ is 1k. Get the optional Bump Mapped files if you're using those.
* [Dragon Statue Replacer](http://www.nexusmods.com/morrowind/mods/43218/?) - I used Option 3. Also incorrectly archived, you'll need to download it manually and extract it, then you can use MO2 to install one of the .rar files from there.
* [Apel's Lighthouse Retexture](http://www.nexusmods.com/morrowind/mods/42532/?) - Get the version you want, bumps or no bumps.
* [Shacks, Docks and Ships](http://www.nexusmods.com/morrowind/mods/43520/?)
* [Tydz Small Mods](http://www.nexusmods.com/morrowind/mods/44028/?) - Specifically, the Gnisis Fort Roof file. It's a very small change, so feel free to skip it if you want. Also incorrectly packaged. You'll need to extact it, drop the `x` folder into a `Meshes` folder and then re-pack it.

3. Hlaalu.

Choose between the following three options. Compare the screenshots from both modpages and see what looks more visually appealing to you. They are similar enough, so if in doubt just get Aesthesia for it's more 'vanilla' look.

* [Aesthesia - Hlaalu textures](https://www.nexusmods.com/morrowind/mods/46009) - Slightly more vanilla friendly option, includes custom meshes and all textures are uniformed under either 1k (MQ) or 2k (HQ) standard. However the textures appear more blurry then in Arkitektora.
* [Hlaalu - Arkitektora Vol.2](https://www.nexusmods.com/morrowind/mods/46246) - Appears more sharper and goes in a bit of a different direction then Aesthesia. Contains no custom meshes and textures resolutions are mixed. Remember to grab the texture replacer for project atlas in the optional files section. Download the version that's in the same quality as the textures you downloaded.
* [Static's AoF Hlaalu Atlased](https://www.nexusmods.com/morrowind/mods/46022) - An Atlas'd version of AnOldFriend's [Hlaalu Retextured](https://www.nexusmods.com/morrowind/mods/28061) by StaticNation. Signiciantly less vanilla-friendly that the other options, but may be a welcome change of pace to some. 

4. Redoran
* [Redoran Bump mapped](http://www.nexusmods.com/morrowind/mods/42406/?) - A note on these: they use extremely high poly meshes. If you're already struggling to top 30 FPS in exteriors, you should pass on these.
* [Redoran Bump mapped fixes](https://mega.nz/#!qkY2gRJI!VSHQoMctyzL9X2qGYXMC2qqN7kHmu2kzsF--nwBJR2Y) - You only need these if you're using bump maps, obviously.
* [Redoran - Arkitektora Vol.2](https://www.nexusmods.com/morrowind/mods/46235) - An improved version of Redoran - Arkitektora. Completely over-writes all files from the original, so that mod is not needed, only this one. Remember to grab the texture replacer for project atlas in the optional files section. Download the version that's in the same quality as the textures you downloaded.

5. Telvanni
* [Telvanni Mesh Improvement](http://www.nexusmods.com/morrowind/mods/42343/?) - If you're bump mapping, nearly everything here will be overwritten. If you're only using textures, grab this.
* [Telvanni Bump Maps](http://www.nexusmods.com/morrowind/mods/42431/?) - Just get the Interiors WIP, it includes all files from the Exteriors as well.
* [Telvanni Arkitektora](http://www.nexusmods.com/morrowind/mods/43530/?) - Has a few problems, easy to fix.

```
1) disable textures\tx_metal_silver.
2) If you're only using the textures, you'll need to download the Bump Maps file anyway and get the bark_01_nm texture and put it in your `textures` folder.
3) If you're using bump mapping, disable meshes\x\ex_t_manor_01 and meshes\x\ex_t_manor_02, use the ones from Telvanni Bump Maps instead.
```

* [Telvanni Fireplace Replacer](http://www.nexusmods.com/morrowind/mods/43232/?)
(Has an alternate look you can choose instead in the `Alternate` folder=, if you like)

6. Vivec & Velothi

Choose one of the following two:

* [Vivec and Velothi - Arkitektora Vol.2](https://www.nexusmods.com/morrowind/mods/46266) - Remember to grab the texture replacer for project atlas in the optional files section. Download the version that's in the same quality as the textures you downloaded.
* [Vivec-Velothi Retextured V3](https://www.nexusmods.com/morrowind/mods/32886) - A bit lower res in many areas, also not Atlas'd so if you're on a lower end machine you may want the other option. However, some vastly prefer the style here.

Then take all of these:

* [Set in Stone](http://www.nexusmods.com/morrowind/mods/21377/?)
* [One True Faith](http://www.nexusmods.com/morrowind/mods/43810/?)
* [Ministry of truth Bump mapped](http://www.nexusmods.com/morrowind/mods/42921/?) - If you don't want bump maps, the only file you need is `textures\tx_moon_base_01`.
* [Sewers Arkitektora](http://www.nexusmods.com/morrowind/mods/43144/?) - There are pictures showing the difference between the versions in the user uploaded images area.
* [Concept Art Ghostfence Replacer](http://www.nexusmods.com/morrowind/mods/43316/?) - Optional, because it IS a departure from the vanilla game. However, it looks great and it's based on concept art. Your choice.

7. Dwemer & Mournhold
* [Connary's Old Mournhold](http://www.fullrest.ru/files/connarysoldmournhold/files)
* [The Mourning of Bamz-Amschend](http://mw.modhistory.com/download-56-11888) - Only three of these textures are better than what we have: `tx_coilcopper00`, `tx_coilcopper01` and `tx_coilcopper02`. Grab only those three files and disable the rest. This is optional, because the textures in question are just 4x magnified vanilla textures.
* [The Clockwork City](http://mw.modhistory.com/download-56-12886)
* [Clockwork City Reborn](http://www.nexusmods.com/morrowind/mods/38369/?) - These textures are less vanilla friendly than the ones above, but in my opinion look much nicer. Use either this OR the previous mod, not both.
* [Green Marble Mournhold](http://mw.modhistory.com/download-64-11859) - Use the alternate walls but not the alternate roof.
* [Full Dwemer Retexture](http://www.nexusmods.com/morrowind/mods/44264/?) - This also covers armor, weapon and creature textures. Grab the "Only Architecture" archive of your preference for now. MQ is 1024, HQ is 2048.

7. Daedric Ruins
* [Daedric Ruins Retexture](http://www.nexusmods.com/morrowind/mods/39125/?)
* [Daedric ruins bump mapped](http://www.nexusmods.com/morrowind/mods/43318/?) - If you want bump mapping.
* [Daedric Ruins Arkitektora](http://www.nexusmods.com/morrowind/mods/43486/?) - Get the bump map patch if you're using the bump-mapped mod from above.
Feel free to choose between Lougian and Tyddy's packs, if you prefer Lougian's then by all means don't get Tyddy's. Tyddy's is closer to vanilla, as usual.

8. Miscellaneous Architecture Pieces
* [Stronghold Retexture](http://www.nexusmods.com/morrowind/mods/43948/?) - Get the vanilla friendly floor tiles if you want it. However, if you do, it will need to be un-packed, all textures moved into a `textures` folder and then re-packed to install properly with MO2.
* [Connary's 6th House](http://www.fullrest.ru/files/connarys6thhouse/files)
* [Road Marker retextured](http://www.nexusmods.com/morrowind/mods/28311/?) - Incorrectly archived. You'll need to unzip it and move the textures into a `Textures` folder, then re-zip it and install as normal.
* [Banners retextured](http://www.nexusmods.com/morrowind/mods/21405/?) - Some of these will be overridden by Django's Tapestries
* [Arukinn's Better Banner Signs and Signposts](http://www.nexusmods.com/morrowind/mods/41658/?)
* [Signposts Retextured](http://www.nexusmods.com/morrowind/mods/42126/?) Optional, as they are lower res than Arukinn's, but if you want signposts readable in English this is what I would recommend. Download the Tamriel Rebuilt patch if using TR.

```
Q: What about Bloodmoon?
A: Bloodmoon textures were actually covered in nature when we downloaded STOTSP. Remember, STOTSP is a massive overhaul of Solstheim's entire landscape. You do NOT need to activate the .esm if you only wanted to use it as a texture/mesh replacer.
```

## Miscellaneous replacers

For things that don't really fit in any other section. If some of this seems too tedious to you, feel free to skip it. Some of this is pretty nitpicky...

1. Book mods
* [Arukinn's Better Books and Scrolls](http://www.nexusmods.com/morrowind/mods/43100/?)
Illy's Dirty Books was an alternative to this, but was removed along with all of Illy's other mods, so you won't find it anymore except through hand-me-downs.
* [Melchior's Magnificent Manuscripts](https://www.nexusmods.com/morrowind/mods/45626) - Has an extra folder (`patches`) that offers compatibility with the mod [Book Jackets](http://mw.modhistory.com/download-56-10464). Un-pack the mod, merge the `patches` folder contents in, and re-pack if you'd like that.

2. [Papill6n's various graphic things.](http://www.nexusmods.com/morrowind/mods/39122/?) Grab the following in the BAIN installer:

```
Awning woven
Bellow and canvaswrap
Bridge rope brown
Fabric on the imperial altar - more vanilla
Kneeling stool - Get the texture only, we have a better mesh
Logo crate
Loom
Ropes - only tx_rope_heavy_01
Ropes woven + hammock's pillow
Rusted metal
Siltstrider - We'll be installing Vurt's silt strider soon.
Soulgems - Take a look at the included screenshot, you'll want to know what they look like for later.
Spinningwheel + spool
Tribunal required
```

Note that "tribunal required" actually has 3 sub-packages. You'll need to open the mods in your `MODS` folder in MO2, then take the contents of each of those 3 folders and drag them into the main mod. It should be obvious which 3. You can delete the .jpg files, don't need them anymore.

3. Qarl's mods. The following mods were made by Qarl but uploaded by someone else on fullrest.
* [Plates](http://www.fullrest.ru/files/Plate_Items/files)
* [Bowls](http://www.fullrest.ru/files/Bowl_Items/files) - disable the following after installing, we have better:
```
meshes folder
textures\q_wood_plate
```

4. Other, less complicated to install mods
* [Connary's Fine Vials](http://www.fullrest.ru/files/connarysfinevials/files) - disable `tx_rustedmetal00.dds` and `tx_rustedmetal10.dds`, Morrowind Visual Pack has better versions of these.
* [Connary's Mixed Pottery](http://www.fullrest.ru/files/connarysmixedpottery/files) - disable the mottled texture and pewter 1 texture.
* [AOF Containers](http://www.nexusmods.com/morrowind/mods/32427/?) - disable `meshes\m\misc_com_bucket_01`.
* [Small Mods by Wolli](http://www.nexusmods.com/morrowind/mods/42453/?) - Just get Darker Crates to match AOF's barrels.
* [Better Kegstands](http://www.nexusmods.com/morrowind/mods/37708/?)
* [Apel's Various Things - Sacks](http://www.nexusmods.com/morrowind/mods/42558/?) - Bump mapped or not, it's your choice. Has an optional patch on the Nexus page for Animated Containers, if you use it.
* [Dahrk Mods by Melchior](http://www.nexusmods.com/morrowind/mods/43528/?) - Get Detailed Brooms.
* [Dunmer Lanterns Replacer](http://www.nexusmods.com/morrowind/mods/43219/?) - Make sure to look at the images and choose the textures you want.
* [EKM Vanilla-Based Paper Lanterns](http://www.nexusmods.com/morrowind/mods/43837/?)
* [AST beds texture replacer](http://www.nexusmods.com/morrowind/mods/21970/?) - We only want the chair mesh, the rest will be overwritten with the next mod.
* [Illy's Bedspreads](http://www.nexusmods.com/morrowind/mods/43565/?) [Wayback link](https://web.archive.org/web/*/http://download.fliggerty.com/file.php?id=1108)
* [Illy's Hot Pots](http://www.nexusmods.com/morrowind/mods/43206/?) - Make sure to activate the .esp. [Wayback link](https://web.archive.org/web/*/http://download.fliggerty.com/file.php?id=1054)
* [AST redware texture replacer](http://www.nexusmods.com/morrowind/mods/21981/?)
* [Improved Better Skulls](https://www.nexusmods.com/morrowind/mods/46012) - In the BAIN installer, do *not* check the "Vanilla Textures" option.
* [Dunmeri Urns Aestetika](http://www.nexusmods.com/morrowind/mods/43541/?) - Get either the simple retexture or the bump map file, you don't need both.

**NOTE**: If you are using the non-bump-mapped version, it's missing `tx_urn_01_nm.dds`. You have a couple options to remedy this:

1: Use the bump-mapped version instead

2: Download both, and copy the missing file from the bump-mapped version over to the non-bump-mapped version

3: Download just the non-bump-mapped version, and copy `tx_urn_01.dds`, and re-name it to `tx_urn_01_nm.dds`

* [Cart Cloth Retexture CCR](http://www.nexusmods.com/morrowind/mods/21837/?) - I prefer the normal version.
* [Propylon Pillar Retexture PPR](http://www.nexusmods.com/morrowind/mods/19600/?) - Get either PPR_Glow or PPR_Normal, then get the PPR_Index Addon.
* [Soulgem Ingredient Retexture SIR](http://www.nexusmods.com/morrowind/mods/19467/?) - If you have both expansions (you should!) then pick SIR_TBandBM_v3 to download. Disable the Stahlrim texture or load this mod just prior to Tyd Landscape Texture Compilation, so that its Stahlrim texture takes precedence.
* [Ingredients Mesh Replacer](http://www.nexusmods.com/morrowind/mods/44067/?) - You'll need to extract the archive, copy the compatibility mesh folder over and over-write the files from the main mod, then re-pack and install as normal.
* [Crabmeat Ingredient Replacer](http://www.nexusmods.com/morrowind/mods/43387/?)
* [Django's Rugs and Tapestries](http://www.nexusmods.com/morrowind/mods/36872/?)
* [Detailed Tapestries](http://www.nexusmods.com/morrowind/mods/22551/?)
* [Shiny Septims](http://www.nexusmods.com/morrowind/mods/42113/?) - I get the dulled version.
* [Ghostnull's Silverware Enhancer](http://mw.modhistory.com/download-44-10088)
* [Better Blood Morrowind](http://www.nexusmods.com/morrowind/mods/33426/?)
* [Flash's Minor Retextures](http://www.nexusmods.com/morrowind/mods/44322/?) - Contains 2 files. 1 is an alternate Blood texture if you dislike BBM's. It also has a Mist retexture, which you should definitely get.
* [Skeleton and Metal Sparks blood retexture](http://www.nexusmods.com/morrowind/mods/43359/?)
* [Improved Cavern Clutter](https://www.dropbox.com/sh/l1660o8fg664bii/AABLfGQtcBsb0jfTsftnBZ-ca/Improved%20Cavern%20Clutter?dl=0) - Download as zip. You'll want to disable the three wood_weathered and rope_heavy textures after installing.
* [Insanity's Potion Replacer](http://tesalliance.org/forums/index.php?/files/file/1402-insanitys-potion-replacer/) - You'll need an account to download these.
* [Insanity's Soul Gem Replacer](http://tesalliance.org/forums/index.php?/files/file/1397-insanitys-soul-gem-replacer/) - Again, you'll need an account. If you like the look of these soul gems better than Papill6n's, go ahead and get them, overwriting your existing meshes. If you like the ones you already have, you might want to download it anyway, as Papill6n's didn't include a retexture of grand soul gems. If you just want the grand soul gems from this mod, get only the following files: `meshes\m\misc_soulgem_grand` and `textures\tx_soulgem_grand`.
* [Insanity's lowres](https://mega.nz/#!T4pB3TCY!wQ3okENYSVv8T2PpHW5jxPiPkbOGrlXBP7ODVglGsDA) - Insanity's replacers are really high resolution for such small objects, especially in an old game like Morrowind. If you'd like, you can get these resized textures instead. You'll still need the original mods for the meshes! Also, if you're only using the grand soul gem, make sure to disable the other textures in the `soulgems` folder, as you don't need them.
* [Long Live The Glassware](http://www.nexusmods.com/morrowind/mods/44016/?) - disable `tx_metal_strip_02`.
* [Long Live The Limeware](http://www.nexusmods.com/morrowind/mods/44045/?)
* [R-Zero's Random Retextures](http://www.nexusmods.com/morrowind/mods/44025/?) - The original guide (2017) existed when only the Coin and Quill (the oldest mods) were available. Now that more exist, I recommend installing all of them. The servent's skull replaces one from Better Skulls (but is based upon BS), so compare them before deciding. The Chimney smoke and Towershield overwrite patch-for-purists, so consider that as well.

## Creature replacers

1. Let's start off with our base. We're starting off with Darknut's suite of textures.
* [Darknut's Creature Textures](http://www.nexusmods.com/morrowind/mods/43420/?)
* [Darknut's Creature Textures addendum](http://www.nexusmods.com/morrowind/mods/43441/?)
* [Darknut's Creature Textures TB](http://www.nexusmods.com/morrowind/mods/43421/?)
* [Darknut's Creature Textures BM](http://www.nexusmods.com/morrowind/mods/43422/?)

2. Connary made some good creature retextures.
* [Storm Atronach](http://www.fullrest.ru/files/connarysstormatronach/files)
* [Skeleton](http://www.fullrest.ru/files/connarysskeleton/files)
* [Lich](http://www.fullrest.ru/files/connaryslichking/files)
* [Kwama](http://www.fullrest.ru/files/connaryskwama/files)
* [Imperfect](http://www.fullrest.ru/files/connarysimperfect/files)
* [Bonewalker](http://www.fullrest.ru/files/connarysbonewalker/files)
* [Daedroth](http://www.fullrest.ru/files/connarysbaddaedroth/files)

3. And now for other replacers
* [Correct UV Mudcrabs](http://www.nexusmods.com/morrowind/mods/42130/?) - Not strictly a retexture, but does improve visuals. I chose the "regular" option rather than smoothed. No screenshots exist to compare the two, but you can test this easily yourself by installing it twice (once for each option) and test enabling/disabling them in-game to see what looks better. [TODO]
* [Guars Aendemika](http://www.nexusmods.com/morrowind/mods/42521/?)
* [Kagouti Aendemika](http://www.nexusmods.com/morrowind/mods/42523/?)
* [Alit Aendemika](http://www.nexusmods.com/morrowind/mods/42520/?)
* [Ash Vampire Reworked](http://www.nexusmods.com/morrowind/mods/43633/?)
* [Cliffracer Replacer](http://www.nexusmods.com/morrowind/mods/43925/?)
* [Nix-Hound Replacer](http://www.nexusmods.com/morrowind/mods/43620/?)
* [Scamp Replacer](http://www.nexusmods.com/morrowind/mods/44314/?) - You (probably) don't need the PBR files, it's still a bleeding edge new feature in MGE XE. Get the Creeper replacer if you want. If you do, make sure to mark the german version of the ESP as "optional" instead of "available" in MO2, since it'll be automatically activated.
* [Luminous Atronachs](http://www.nexusmods.com/morrowind/mods/42613/?)
* [Vurt's Silt Strider Retexture](http://www.nexusmods.com/morrowind/mods/30696/?) - Don't use skin_01 and skin_03, we have fixed versions of those.
* [Netch Bump mapped](http://www.nexusmods.com/morrowind/mods/42851/?) - Get the main file and the optional file.
* [HiRes Scrib](http://www.nexusmods.com/morrowind/mods/43352/?) - I use the bright version.
* [Unique Winged Twilights](http://download.fliggerty.com/download--743) - The .esp is unnecessary if you just want a replacer.
* [BB Dwarven Spectre](http://www.nexusmods.com/morrowind/mods/29671/?)
* [Better Almalexia](http://www.nexusmods.com/morrowind/mods/23388/?) - Comes with alternate pupil textures if you want. (You'll need to unpack/repack to add them). Also comes with a splash screen (`Splash_almalexia.tga`) you may want to disable if you feel it clashes with the existing splash screen replacers.
* [Azura Replacer](http://mw.modhistory.com/download-45-6053)
* [Vivec God Replacement Creature Edition](http://mw.modhistory.com/download-26-10946) - Feel free to look through the `extras` folder at your options.
* [Voiced Vivec and Yakety Yagrum](http://www.nexusmods.com/morrowind/mods/40994/?) - Not really a replacer, but it does involve 'creatures'.

We'll get a replacer for Dagoth Ur and dremora in the next step.

>Q: What about *insert other creature replacer here*?
>
>A: Dwemer will be installed in the next section. Otherwise, I don't know about it, or it didn't suit my taste. Here's a small list of mods I know about that didn't make the cut:

* Connary's other replacers - fullrest.ru
* Silt Strider Bump Mapped by Lougian - Nexus
* Psy's Golden Saint Replacer - MMH
* Divine Dagoth Ur - Nexus
* Spriggan Bump Mapped by Lougian - Nexus
* Kwama Forager Bump Mapped by Lougian - Nexus, specifically in his Various Little Mods
* Tyddy's Cliff Racer Aendemika - Nexus

## Heads & Bodies

1. Heads

  *Note that if you're using any of the head replacers linked here in conjunction with Tamriel Rebuilt the heads provided by TR will not be fully replaced because the mod assigns unique IDs to their heads.*
* [Westly's Pluginless Head and Hair Replacer](http://download.fliggerty.com/download-127-874)
* [Various tweaks and fixes](http://www.nexusmods.com/morrowind/mods/43795/?) - Specifically, Fixed Westly's Female Orc Heads and Fixed Westly's Barenziah Head.
* [Animation Fix for Westly's Heads](https://www.dropbox.com/s/d70l2lbjvm0xloo/WestlyHeadsAnimFix.zip?dl=0) - Does what it says on the tin
* [Pluginless Khajiit Head Pack](http://www.nexusmods.com/morrowind/mods/43110/?)
* [Pluginless Khajiit Head Pack Talk-Blink Fix](http://abitoftaste.altervista.org/morrowind/index.php?option=downloads&task=info&id=68&Itemid=50&-Pluginless-Khajiit-Head-Pack-Talk-Blink-Fix)

Argonians will be in the next step, don't worry.

2. Body replacers
* [Robert's bodies](http://www.nexusmods.com/morrowind/mods/43138/?) - Get the main file. Optionally, get Dagoth Ur OR Dagoth Ur (vanilla shaders), and RB Altmer Females, RB Bosmer Females, RB Orcish females. For the last three, you should look at the images section to see what they do; Westly's version is on the top, RB version is in the middle, and vanilla is at the bottom. The Orcish heads in particular are a drastic departure from Westly's heads, but they look more like the vanilla heads.
* OR [Install Better Bodies](https://www.nexusmods.com/morrowind/mods/3880)

Note that at the time this guide is being written, Khajiit meshes are nude. If seeing girl Khajiit nipples bother you, you should consider activating New Beast Bodies Khajit - Clean below, and make sure it overrides Robert's bodies. Also, it seems the nude meshes have been removed.

* [Unique Shadow Pack](http://mw.modhistory.com/download-10-6029) - This is a requirement for...
* [New Beast Bodies - Clean Version](http://mw.modhistory.com/download-10-10928) - You only need the argonian .esp and files. If you want to disable the khajiit meshes and textures, you can, but as long as you don't activate the Khajit bodies .esp, you'll be fine leaving them alone.
* [Want 'mature' Argonians?](http://mw.modhistory.com/download-10-11364)
* [Wey's Argonians](http://www.nexusmods.com/morrowind/mods/43766/?) - Get the optional darker mouths file if you wish. I use it.

An newer alternative to Wey's Argonians for those who feel it may stray too far from the vanilla style:

* [Improved Argonians](https://www.nexusmods.com/morrowind/mods/45918)

## Clothes replacers

1. Clothes
* [Better Clothes](http://www.nexusmods.com/morrowind/mods/42262/?) - Get the non-installer version. Only activate ONE esp. If you want your Argonian females to have breasts lumps when clothed for some reason, choose the regular version. For flat Argonian females, choose the nac version.

Note: When opening Mlox, it will warn you that Better Clothes depends upon Better Bodies. This is not unwarrented, there will be clipping. You can ignore this if you like, or install Better Bodies instead of Roberts bodies. Robert's bodies is heavily incompatible with other mods, including later mods in this guide. You also have a couple alternative options:

> 1) Get the [Robert's Bodies version of Better Clothes on Wolflore](http://wolflore.net/viewtopic.php?f=15&t=2086&sid=53e89d737b9bbe081c9946c8c0d11b37) (Requires an account, also that link may not be right)
> 2) Forgo Better Clothes and use something like Articus' clothes which are compatible with both Robert's and Better Bodies

* [Common Shirt Fix](https://www.dropbox.com/s/3kwi2ha2anpu7kw/BCFix.zip)
* [BC Shoes Fix](https://www.dropbox.com/s/usgjr6hwi53c6ma/BC%20Shoes%20Fix.zip)
* [Expensive Female Shirt Fix](http://mw.modhistory.com/download--14998) - Put this in your `meshes\BC` folder.
* [More Better Clothes](http://mw.modhistory.com/download-53-6647) - Get both the main file at the bottom of the page and the `MBC_ArmsFix`.
For the Arms fix, make sure to un-pack it and create a `Meshes/BC` folder, then drop those .nif files into it and re-pack it to install.
* [Better Clothes for Tribunal](http://mw.modhistory.com/download-87-11804)
* [Better Clothes Bloodmoon Plus](http://download.fliggerty.com/download-21-804) ([Wayback Link](https://web.archive.org/web/20161103154305/http://download.fliggerty.com/file.php?id=804))
* [BCBM Pants Fix](https://www.dropbox.com/s/lkxditr9gl3a92c/BCBM_Pants_Fix.zip)
* [Darknut's Better Clothes Textures](http://www.nexusmods.com/morrowind/mods/43423/?)

2. Accessories.
Note that some of this stuff (belts, rings, amulets) won't actually appear on your character model. It's mostly so your unique, fancy stuff will look cool when you painstakingly put it on shelves in your stronghold later.

* [Drakkmore's Plugginless Ring Replacer](http://download.fliggerty.com/download-136-1035) - Actually gives rings textures. Without this they'll all appear black in-game.
Alternative: [Unique Jewelry Redone](https://www.nexusmods.com/morrowind/mods/46151)

* [Unique Finery Replacer UFR](http://www.nexusmods.com/morrowind/mods/25725/?) - Activate the regular version. The robe mod we'll be using comes with a compatibility patch.

3. Robes
* [Better Robes](http://www.nexusmods.com/morrowind/mods/42773/?) - Make sure to also install the patches for Tamriel Rebuilt if you're using it (`TR` folder) and UFR. If you plan to use Animated Morrowind, download the separate patch for that as well.
If using the Tamriel Rebuilt patch, you'll need [this patch]([https://www.nexusmods.com/morrowind/mods/44875]) for that patch, since it crashes due to a Master reformat in 2015 that TR did. (However, note that this doesn't fix the issue with the TR-BR-Animated Morrowind combined patch, so you're kind of out of luck if using that)
* [Robe Overhaul](http://www.nexusmods.com/morrowind/mods/43748/?)
* [Various tweaks and fixes](http://www.nexusmods.com/morrowind/mods/43795/?) - Optionally, if you hate the glow effect that Robe Overhaul adds to some robes, you can download Blank Glow Maps for Robe Overhaul. You might also be interested in Pluginless NoGlow Lite, which removes the plastic-y looking 'enchantment' effect from all items in game.

## Weapons

There are likely a ton of unique/artifact weapon replacers I've missed. I was never very good at keeping track of weapon mods...
* [Darknut's Little Weapons Mod Complete](http://www.nexusmods.com/morrowind/mods/43418/?) - Has a `Textures/512` and a `Textures/1024` folder. You should be able to handle the 1K textures. In either case, you'll need to un-pack the mod and move all of whichever you choose (in my case 1K) out of the `1024` folder, and into the root `Textures` folder, and then re-pack.
* [Oriental Mesh Improvements](http://www.nexusmods.com/morrowind/mods/29906/?)
* [Crossbows](http://download.fliggerty.com/download-98-1010) - If you don't want the new crossbows, don't activate the .esps. You'll still get new meshes for the base game's crossbows. For TR users, note that this ESP is also set up requiring the old TR's ESP as a master, so it'll crash if you try to use it on modern TR installs.
* [Real Reflective Weapons - Iron](http://www.nexusmods.com/morrowind/mods/43077/?) - Install the base (`Data Files`) folder and the `bonus` folder.
* [Improved Weapon Meshes - Steel](http://www.nexusmods.com/morrowind/mods/43120/?) - Install `00` and `01`. You do not need the .esps.
* [Improved Weapon Mehses - Ebony](http://www.nexusmods.com/morrowind/mods/43484/?) - Install `00`. Install `01` if you want an Ebony Claymore in your game, you'll need the .esp. If you do so, note that it'll install two ESPs, one being german. Make sure to move it to the "Optional" section after installing.
* [Dwemer Armoury](http://www.nexusmods.com/morrowind/mods/43335/?) - Unfortunately, this isn't totally compatible with our armor mods, but the weapon meshes and a few of the armor meshes will show up in game.
* [Mehrunes Razor Replacer - Oblivion](http://www.nexusmods.com/morrowind/mods/23825/?)
* [True Trueflame](http://www.nexusmods.com/morrowind/mods/33432/?)
* [HopesFire Replacer Morrowind Edition](http://mw.modhistory.com/download-98-12378) - Has an `AltTextures` folder with an alternate texture for Hopesfire. You'll need to move the folder's contents into the `Textures` folder and over-write after installing if you wish to use it. (Or un-pack/repack)
* [Various little mods](http://www.nexusmods.com/morrowind/mods/43330/?) - Volendrung.
* [Improved Weapon Meshes & Textures WIP](https://www.dropbox.com/sh/l1660o8fg664bii/AAAO3m96a4O4J4JOUOUBmFnFa/Improved%20Weapon%20Meshes%20%26%20Textures%20WIP?dl=0) - Download as .zip.
* [Spear-Staff Fix](http://www.nexusmods.com/morrowind/mods/43353/?) - An optional fix for the position where spears and staves are held. Use it if you want. If you do, you'll need the Real Reflective Weapons Iron, Improved Weapon Meshes Steel, and Improved Weapon Meshes Ebony files from the `Compatibility` folder, then drag them out and over-write their normal versions from the mod, and then re-pack and install as normal.

## Armor
* [Darknut's Armor Textures](http://www.nexusmods.com/morrowind/mods/43416/?) - Make sure to get the newest version.
* [Various little mods](http://www.nexusmods.com/morrowind/mods/43330/?) - Install Colovian helm and Dust adept helm.
* [Improved Armor Parts](https://www.dropbox.com/sh/l1660o8fg664bii/AAB-OssUyNu03Y5aGCO1Gav0a/Improved%20Armor%20parts?dl=0) - Download as .zip. First, disable the `Bloodmoon` folder; you already have that mesh. If you want a less bulky chitin pauldron, put that in `meshes\a`. Then go ahead and install the `meshes` and `texture` folder.
* [Various tweaks and fixes](http://www.nexusmods.com/morrowind/mods/43795/?) - Get Lougian's Colovian Helm fix.
* [HiRez Armors - Native Styles](http://forums.bethsoft.com/topic/1441431-relz-hirez-armors-native-styles-v2)
* Native HiRez fix [1](http://forums.bethsoft.com/topic/1441431-relz-hirez-armors-native-styles-v2/page-2#entry22297270), [2](http://forums.bethsoft.com/topic/1441431-relz-hirez-armors-native-styles-v2/page-2#entry23936622), [3](http://www.mediafire.com/file/sj9kg66x5cdq45l/tx_armor_EXC.dds/file) (Fixes missing `tx_armor_exc.dds`, just put it into the `textures` folder of whichever of these fixes mods has a `textures` folder)

* [Armor Retexture - Outlander Styles](http://www.nexusmods.com/morrowind/mods/44210/?) - Get the HQ version. You don't need to get the dragonscale armor textures. You should disable `tx_a_templar_helmet` before installing; we have better from Improved Armor Parts.
* [Full Dwemer Retexture](http://www.nexusmods.com/morrowind/mods/44264/?) - We already installed the architecture, now we need the 'Only armor, robots, and weapons' archive. Pick MQ (1024) or HQ (2048), your choice.

* [Better Morrowind Armor](http://www.nexusmods.com/morrowind/mods/42509/?) - This needs more detailed install instructions:

1) First, extract the archive. You need to activate the Better Morrowind Armor .esp, and optionally one DeFemm patch of your choice. A removes all female versions of cuirasses (no boob armor for females), O acts like the original game, R gives boob armor only to stretchy armor. You should use the DeFemm patches from [here](https://mega.nz/#!WgBQ2RRL!5CQU0ZIVvIv1vyUsutAr3gT-qMhRUU6E4KEvoE9AeOs).

2) Install the patch for Hirez Armors - Native Styles V2. (Overwriting the main files in the mod folder)

3) If you're using the LeFemm official plugin for Bethesda, install the patch for that as well. That means DEACTIVATING the `[Official]LeFemm Armor.esp` included with WHReaper's Morrowind Official Plugins and using the `LeFemmArmor.esp` included with Better Morrowind Armor.

(Personally, I still haven't figured out how I want to handle the official addons since both options are giving me trouble, so as of writing this guide I am *personally* just ignoring all Defemm/LeFemm patches and just using the Hirez Native styles patch)

* [Daedric Lord Armor Morrowind Edition](https://www.nexusmods.com/morrowind/mods/44081) - Not only does this replace Daedric armor and gives bound armor a unique look, it acts as a replacer for Dremora as well. It's newer than Better Morrowind Armor and should load after it, so you'll get this nicer looking Daedric Armor in game.

* [Less Bulky Pauldrons](http://www.nexusmods.com/morrowind/mods/42566/?) - Optionally, you might like this. If you do decide to use it, make sure to use the the BAM & Native HiRez 2 files in the optional folder. To use them, create the folder `Data Files\Meshes\bam` inside the un-packed mod. Then, move the files from the `BAM & Native HiRez 2` into that `bam` folder, not the `a` folder like the rest of the mod's contents.

## Animations

* [Animation Compilation](http://www.nexusmods.com/morrowind/mods/43982/?) - Download the "Fixes by Greatness7" version.

## Finishing Touch

For our last step in adding new meshes and textures we will download a new mod called [Asset Overrides](https://www.dropbox.com/sh/cmf60boahjpdbt9/AACWM1LFtbxtN5pMRxHeClS2a?dl=0) which is a collection of miscellaneous asset files that will either patch or improve the quality of existing assets you've downloaded with this guide. This is where you can place all those asset files you want to override the ones you are currently using. Using MO2 you can just place them in the override folder if you want, but you have less control over them that way.

## MGE XE Setup
If you have a toaster, you might want to skip most of this section. Run distant land as explained towards the end with default settings and see how the game runs for you first before trying out grass or any of the fancier light settings in the distant land tab. You might also try out shaders.

1. Before we begin, we need to download mod(s) related to grass. 
* [Aesthesia Groundcover - grass mod](https://www.nexusmods.com/morrowind/mods/46377/) - An excellent looking and performing grass mod to replace "Azura's Coast and Sheogorath - Grassmod" and "Morrowind grass mod combined v1.0"

Alternatively, you could use...
* [Vurt's Groundcover](http://www.nexusmods.com/morrowind/mods/31051/?) - If you want Ashlands grass, you'll need to download this as none was included in the previous download. Use EITHER the previous mod OR this one for the other regions, do NOT use both. Use Reeds and the optional Corals download if you want, they should work with Grass Mod Combined just fine.

For those of your with Tamriel Rebuilt, [Tamriel Rebuilt - Old Ebonheart Groundcover](https://www.nexusmods.com/morrowind/mods/45973) will enable Vurt's groundcovers to extend to Tamriel Rebuilt.

2. Some other MGE XE relevant mods.
* [Distant Mournhold](http://www.nexusmods.com/morrowind/mods/43255/?) - DO activate this .esp in your launcher.
* [PeterBitt's Small Mods](http://www.nexusmods.com/morrowind/mods/42306/?) - If you have a good computer and plan to use Per Pixel Lighting, get the Negative Lights Remover. Activate in your launcher.

3. Distant Land

First, make sure all the mods you're using are activated in the launcher (again, don't activate the grass mods in the launcher). Then, run MGEXEgui.

>1) Go into your Distant Land tab, and click on Distant land generator.
>
>2) Click 'Use current load order', then go through the list and activate Grass_AC&S, Vurt's Groundcover - The Ashlands (if you're using it), and either Ozzy's Grass packages (there are five total) or Vurt's Groundcover - BC, AI, WG, GL and Vurt's Groundcover - Solstheim. Also, activate the reeds module and the corals module if you're using those. Click continue.
>
>3) On the next few screens, change the settings only if you think it's necessary. If you're running a toaster you might want to turn your settings down. Be very conservative if you try to turn the settings up higher, as it CAN make your game run very slow. That said, re-running the distant land generator isn't too difficult. On the statics screen, make sure to read the tooltips for your options and set them accordingly.
>
>4) When your distant land generation is finally complete, you'll have a lot more options in MGE XE to play with. Most of these are self-explanatory. If you have a toaster, you probably shouldn't mess with the default settings much; instead, launch the game and see how it runs for you with the basic settings, and with shaders, before messing with anything here. If you have a beastly machine, try out Per-pixel lighting (make sure to use Negative Lights Remover.esp in this case), high quality exponential fog, and high quality atmosphere and distance coloring. And, of course, turn up your draw distance. I play with 10 visible cells.
>
>You will by default recieve a warning about 2 missing files - 'tx_ma_sandstone02.tga' and 'tx_lavacrust00.tga'. This is completely normal and is a fault with the vanilla Morrowind BSA - nothing you did. However, if these messages bother you and you want to be sure from a glance whether you have real issues when generating distant land [this mod](https://www.nexusmods.com/morrowind/mods/45588) provides dummies of those two files so the distant land generator doesn't complain.

4. Shaders

Click the shader setup screen on the General tab. If you're using the latest MGE XE beta, the shaders screen will be pretty easy for you. Pick your quality preset, adjust it how you want (turning on/off DoF, using lower quality SSAO, etc.), and play. 

Optionally, you might want to take a look at [this water shader](https://www.nexusmods.com/morrowind/mods/45432).

5. Finally, and only when you're satisfied with your distant land generation, install [Lore-Friendly Ghostfence Texture](http://www.nexusmods.com/morrowind/mods/29206/?). Why is this step last? Because if you generate distant land with these textures in your folder, it makes the ghostfence look like it has holes in it. Stick with the vanilla textures for distant land generation. If you re-run your distant land generation later, try to remove the textures `tx_gg_fence_01` and `tx_gg_fence_02` from your `textures` folder first.

## Cleaning mods

Several of the plugins in this list need to be cleaned. Here is the list of plugins that need to be cleaned, and do not have any warning about cleaning in their readme:

```
wl_SolstheimOverhaul_v1.esm
correctUV Ore Replacer 1.0.esp
What Thieves Guild.ESP
LGNPC_TelMora_v1_30.esp
LGNPC_Khuul_v2_21.esp
Less_Generic_Bloodmoon.esp
Less_Generic_Nerevarine.esp
LGNPC_SecretMasters_v1_30.esp
LGNPC_IndarysManor_v1_51.esp
LGNPC_PaxRedoran_v1_20.esp
Bloated Caves.esp
Graphic Herbalism.esp
Apel's_Asura_Coast_Fix.esp
Vurt's BC Tree Replacer II.ESP
Graphic Herbalism TotSP.esp
VoicedVivec.ESP
YaketyYagrum.ESP
Better Clothes_v1.1_nac.esp
UFR_v3dot2.esp
Better Morrowind Armor.esp
Illy's Hot Pots.ESP
Vurt's Grazeland Trees [Palms].esp
```

To actually clean, first open Wrye using MO2. Then, right click on a given plugin and hit "Clean with TES3CMD"

## Creating a multipatch

This is very easy. Open Wrye. At the top bar, go to "Misc" then "TES3CMD" then "Create Multipatch". It shouldn't take too long.

It will automatically be appended to the end of your load order. You're now good to go!

(Note: You must re-do this upon adding, removing or re-ordering mods)

## Updating Masters, Syncing and Reparing your Saves

This should be done periodically with saves. You *must* do this anytime you add, remove or move a mod while in the middle of a playthrough.

The steps are as follows:

1. Open Wrye
2. Go to the "Saves" Tab
3. Right click anywhere in the plugins list under "Masters" on the right side. A popup should appear asking you if you wish to edit the list. This will automatically perform an update of the masters.
4. Right click on the "Masters" column header, choose "Sync to Mod List"
5. Hit "Save" towards the bottom
6. Right click the save file, and hit "Repair All"

Perform this for each save you have.

All of your saves should appear Purple in the list now. For each save, all of its masters should appear in blue.

## Load Order

If you've followed this guide to the letter, your (almost!) final load order of ACTIVATED .esps should look something like this:
[TODO ONCE COMPLETE]

## Other mods
Not satisfied with just a graphics overhaul? Here are some other mods you might want to try. Please, PLEASE read the mod pages for the following mods. Simply downloading and installing everything here could very well make your game laggy and/or unstable, especially if you don't use a proper load order. I do not play with all of the below mods myself anymore, but have played most of them in the past. Some I've never played at all, but they do come highly recommended and endorsed by the community.

0) I (Thastus) personally very much enjoy [Races Are More Fun](https://www.nexusmods.com/morrowind/mods/21875) and [Birthsigns are More Fun](https://www.nexusmods.com/morrowind/mods/17888)


If you enjoy H2H characters, the MGE XE version of the Opponent Fatigue Indicator can be found [here](https://www.nexusmods.com/morrowind/mods/44363)
(However, it does not scale with MGE XE gui size options. Not sure if possible to modify it to do that.)

As well, I find [MCC leveler](https://www.nexusmods.com/morrowind/mods/44294) (inspired by GCC and MADD) to be the best leveling system for my personal tastes (Others certainly exist though).

(Includes optional state-based HP, optional state-based magicka regen, as well as just a better overall leveling system inspired by MADD and GCD, but far better than either. No micro-management required! No min-maxing needed!)

1) Want your sound overhauled as well as your graphics? You can try [Atmospheric Sound Effects](http://mw.modhistory.com/download-51-7148).

2) Are you used to newer Bethesda games where you swing your weapon and hit the enemy without miss chance? Is Morrowind absolutely ruined for you if your attacks miss when it clearly looks like they should hit? [Try this](http://forums.bethsoft.com/topic/1513002-rel-oblivion-like-combat-tweaks-part-of-men-project/).
*Disclaimer: I haven't tried this. But reading through the forum thread, it seems like a solid mod.*

3) Want to be a wizard? Here are some mods you might like.
* [Mastering Magicka](http://www.nexusmods.com/morrowind/mods/42269/?) - This mod is a complete overhaul of the magic system. You should read the mod page to learn more. If you think this mod does a little too much and want to pick and choose for yourself, keep reading.
* [Fair Magicka Regen](http://www.nexusmods.com/morrowind/mods/39350/?)
* [Raym's Simple Mana Regeneration](http://www.wolflore.net/viewtopic.php?f=108&t=1553.) - Use either this OR Fair Magicka Regen, not both. The difference between them is that Fair Magicka Regen is percent based, while Raym's is simpler and lighter on CPU/scripting. Don't use this OR Fair Magicka Regen if you're using Mastering Magicka.
* [Spell Cast Reduction](http://mw.modhistory.com/download-37-1406) - Another feature already included in Mastering Magicka.
* [Melian's Teleport Mod](http://mw.modhistory.com/download-21-6360) - Not only for wizards, this mod makes travelling around Vvardenfell a breeze by allowing you to have unlimited Mark locations.

4) Do you want to travel with a companion (or two)? Then here is, hands down, the best Morrowind companion mod: [Julan, Ashlander Companion](http://lovkullen.net/Emma/Kateri.htm) You might also be interested in [this](http://www.nexusmods.com/morrowind/mods/42780/?) to give Julan plus some other characters the fully red Dunmer eyes. There are some other companion mods for Morrowind, but none with the sheer amount of dialogue (or a quest directly tied to the main plot) as Julan.

5) Want harder dungeons?
* [Darknut's Greater Dwemer Ruins versions 1.1](http://www.nexusmods.com/morrowind/mods/43544/?) - A widely used and recommended overhaul of the Red Mountain citadels. It was mentioned in Knots' guide. It's incompatible with most anything that changes any of the final dunmer strongholds. Unfortunately, most of the compatibility patches for it only work with 1.0, which can be found [here](http://mw.modhistory.com/download-98-11646).
* [Sotha Sil Expanded](http://www.nexusmods.com/morrowind/mods/42347/?) - Trainwiz's epic mod that completely overhauls the final dungeon of the Tribunal expansion. Conflicts with anything changing Tribunal's final dungeon. Less Generic Tribunal MIGHT be fine, as I can't find anything saying otherwise, but I haven't made it far enough in the Tribunal story to test it with the newest version of LG Tribunal.

6) Want a mod that gives your character needs?
* [Necessities of Morrowind](http://mw.modhistory.com/download-53-12114) - For a long time, NoM was the only needs mod Morrowind had. NoM has several compatibility patches with various mods as a result. Seeing as it actually adds quite a bit to the world with food stalls, restaurants, wells, etc., if you plan to use NoM you should keep compatibility in mind. Alternatively, you could use the new mod The Bare Necessities instead, which strips out a lot of things NoM added (world changes, new items, cooking system) while keeping hunger, thirst and the need for sleep. You can find it [here](http://www.nexusmods.com/morrowind/mods/43365/?).

7) Want kids in your game?
* [Children of Morrowind](http://lovkullen.net/Emma/kids.htm) - If you want kids, or to play a teenager, in Morrowind, this mod is your only option. Because this mod adds NPCs and items to settlements all over Vvardenfell, you should keep an eye out for any compatibility patches if you use this mod. And one last thing, CoM was designed in a way to keep the children of the mod safe, meaning if you attack them, they'll teleport away before you can hurt them. This was a design decision on Emma's part, so if you don't like invincible kids, don't download the mod.

8) Want mods to add atmosphere or to give you a more immersive experience?
* [Mountainous Red Mountain](http://www.nexusmods.com/morrowind/mods/42125/?) - Makes Red Mountain much bigger, at the cost of breaking compatibility with any mod centered in and around Red Mountain. It's popular enough that there are compatibility patches for most popular mods. I believe there's a patch for Julan and another patch for Greater Dwemer Ruins 1.0.
* [Gondoliers](http://www.nexusmods.com/morrowind/mods/43291/?), [Boat](http://www.nexusmods.com/morrowind/mods/42270/?), [Silt Striders](http://www.nexusmods.com/morrowind/mods/42267/?). All by abot. Travel in real time instead of instantaneously!
* [Where are all birds going](http://www.nexusmods.com/morrowind/mods/43128/?) and [Water Life](http://www.nexusmods.com/morrowind/mods/42417/?), by the same wonderful guy who made the above mods, adds birds and aquatic creatures to Morrowind.
* [Animated Morrowind](http://abitoftaste.altervista.org/morrowind/index.php?option=downloads&task=info&id=39&Itemid=50) - Adds NPCs to the game with new and unique animations to make the world feel more alive.
* [Starfire's NPC Additions](http://mw.modhistory.com/download-90-13583) - Adds a ton more generic NPCs to the world to make it feel more lively, with a lot less 'getting destroyed by a high level vampire on the road to Pelagiad in the fucking DAYLIGHT' than Morrowind Comes Alive had. (I'm not bitter or anything.)

9) Are you an aspiring member of House Telvanni? The following mods might interest you, and as a bonus, they can all be used together

* [Uvirith's Legacy](http://download.fliggerty.com/download-35-884) - A major revamp of Tel Uvirith, your stronghold if you join and advance in House Telvanni. Make sure to use the RoHT addon from the addons folder if you plan to use Rise of House Telvanni (see below).

Using TR? [Here](https://www.nexusmods.com/morrowind/mods/44656) is a patch for it.

* [Building Up Uvirith's Legacy](http://mw.modhistory.com/download-47-11851) - This mod is a major revamp of Uvirith's Grave, which is the area surrounding Tel Uvirith. If you plan to use Uvirith's Legacy, this is the version of BuUG you should use.
* [Rise of House Telvanni](http://mw.modhistory.com/download-21-10664) - Further expands the Telvanni questline in a major way. Be warned there are some... special characters included in this. Do NOT get the compatibility addon! It's very out of date, use the one from Uvirith's Legacy instead!

>Q: What if I plan to join House Hlaalu or Redoran instead of Telvanni?
>
>A: If you plan to join House Redoran, you're in luck: you've already downloaded and installed the best major overhaul for Redoran around if you've followed this guide to the letter, and that would be the LGNPC suite. The LGNPC mods cover the entirety of House Redoran at this point in time, and a lot of their holdings, making a House Redoran playthrough a lot of fun. Also, the new mod [Bal Isra Rising](http://www.nexusmods.com/morrowind/mods/44329/?) overhauls the Redoran player stronghold. At this time it looks like it MIGHT have some compatibility issues with LGNPC Indarys. If you want to be totally safe, you could play the mod without LGNPC Indarys activated, or you could wait for a compatibility patch from the mod author. If you want to use both, it sounds like the worst case scenario is that you might have to use the console to go to the old version of Indarys Manor to talk to NPCs inside it for new dialogue.

If you're going to join House Hlaalu... well, unfortunately, at this time, there really is nothing I can recommend. There are a few mods that revamp Rethan Manor, but nothing really spectacular stands out. House Hlaalu is still a very vanilla experience.

11) Annoyed with how quickly vanilla torches go out? While you're at it, want them to put out more light? What about lanterns and candles? Try out [True Skyrimized Torches](http://www.nexusmods.com/morrowind/mods/43192/?).

12) Do you feel like you don't get enough benefits from being in a guild? Do you want more perks from joining the Temple or the Cult? Want to boss around your underlings after you become the Head of a House? You might like [Antares' Big Mod](https://arcimaestroantares.webs.com/antaresbigmod.htm).

13) Hate the limited capacity of chests and boxes? Want to get rid of that? [Ta-da](https://www.nexusmods.com/morrowind/mods/45698). Honestly, best mod addition I've added so far. So much hassle and annoyance saved.

## Screenshots

If you would like to see reference images of the finished install, or contribute your own screenshots to that public repository, head to our [Google Drive Gallery](https://drive.google.com/drive/folders/1pusVDmDBMwM1Si28W8wcvVd6aXukEIJr?usp=sharing).

## Contributors

This project wouldn't be possible without the help of all the amazing people I've met on the way. :)

* owlnical - for direct contribution to the github repo, advice and helping to expand and continue the project into the future
* /u/Tiber-Septim - for long comments helping me to find areas to improve the project as they walked through the install process
* Corsair - for putting up with my annoying Discord questions and helping an immense amount from finding mods to understanding technical details of Morrowind modding
* yooksi - for doing an insane amount of work. The updater, tons of mod additions, lots of tweaking and testing, and likely putting in more hours than I have into the project.
* The many people from the Morrowind Modding Discord and /r/Morrowind subreddit who've helped me in many ways.

Thank you all for everything!

## License

This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
