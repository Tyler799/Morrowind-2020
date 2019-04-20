
echo Time to generate a new project changelog
echo Please enter your Github PA token:

# GitHub only allows 50 unauthenticated requests per hour so we need
# to run this script with authentication by using a token
read tokenVar

tokenVar="--token $tokenVar"
username="-u Tyler799"
repoPath="-p Morrowind-2019"

# Use --cache_log /dev/null argument to avoid errors on Windows
cacheLog="--cache_log /dev/null"

# Define all labels you want excluded from the changelog
excludeLabels="--exclude-labels discussion,help wanted,question"

bugsLabel="--bugs-label \"**Fixed problems:**\""
enhancementLabel="-enhancement-label \"**Implemented improvements:**\""

# Issues with the specified labels will be always added to these sections:
enhancementLabels="--enhancement-labels improvement"
bugLabels="--bug-labels problem"

echo $bugsLabel
echo $enhancementLabel

#github_changelog_generator Tyler799/Morrowind-2019 $cacheLog --token $tokenVar $excludeLabels $bugsLabel $enhancementLabel $enhancementLabels $bugLabels

read -p "Press enter to continue"
