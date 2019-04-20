
echo Time to generate a new project changelog
printf "\nPlease enter your Github PA token:\n"

# GitHub only allows 50 unauthenticated requests per hour so we need
# to run this script with authentication by using a token
read tokenVar

tokenVar="--token $tokenVar"
username="-u Tyler799"
repoPath="-p Morrowind-2019"

# Use --cache_log /dev/null argument to avoid errors on Windows
cacheLog="--cache_log /dev/null"

# Define all labels you want excluded from the changelog:
excludeLabels="--exclude-labels task,invalid"

bugsLabel="--bugs-label '**Fixed problems:**'"
enhancementLabel="--enhancement-label '**Implemented improvements:**'"

# Issues with the specified labels will be always added to these sections:
enhancementLabels="--enhancement-labels improvement"
bugLabels="--bug-labels problem"

# Use GitHub tags instead of Markdown links for the author of an issue or pull-request
nameTags="--usernames-as-github-logins"

# The specified labels will be shown in brackets next to each matching issue:
listIssueLabels="--issue-line-labels 'add mod','remove mod',improvement,update,important,problem,request,code,format"

#github_changelog_generator Tyler799/Morrowind-2019 $cacheLog --token $tokenVar $excludeLabels $bugsLabel $enhancementLabel $enhancementLabels $bugLabels
# Include pull requests without labels in changelog
#prWoLabel="pr-wo-labels=false"

printf "\nGenerating your changelog...\n"


read -p "Press enter to continue..."
