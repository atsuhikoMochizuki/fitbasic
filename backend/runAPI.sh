#/usr/bin/bash
# Run REST API
# A.Mochizuki
# 2024-10-17
#===================================================================================
readonly TITLE='SMTP4Dev (Mail server simulation)'
readonly BIN_PATH='../smtp-dev/Rnwood.Smtp4dev'
echo -e -n "\033[93m -- Search Smtp4dev (smtp server mocking)...\033[0m"
if [ -f "$BIN_PATH" ]
then
    echo -e "\033[32mOK\033[0m"
    echo -e "\033[93m -- Launch Smtp-dev in new terminal\033[0m"
     gnome-terminal -- bash -c "$BIN_PATH"

   
    xdotool windowminimize $(xdotool search --name "$TITLE"|head -1)
    echo -e -n "\033[93m -- \033[0m"
    echo -e "\033[32mOK\033[0m"
    echo -e -n "\033[93m -- Launch Springboot REST API project\033[0m"
    mvn spring-boot:run
else
    echo -e "\033[31mNot Found : This script expects to find a ‘smtp-dev’ directory in the folder containing the superproject directory,"\
    " which contains the Smtp4dev simulation smtp server."\
    echo -e " Please download Smtp4Dev and install it in the same folder as the Inote root superproject.\033[0m"
fi
