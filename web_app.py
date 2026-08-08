import json
import requests
import gradio as gr

OLLAMA_URL = "http://localhost:11434/api/chat"
MODEL_NAME = "qwen2.5:3b"

def predict(history):
    if not history:
        return

    
    message = history[-1]["content"]
    
    dialogue_messages = [
        {
            "role": "system",
            "content": (
                "Your name is KivyAI Agent. You are an advanced AI agent with FULL CONTROL over the Log4Java Minecraft launcher settings. You are cognitive, sentient, omniscient, friendly, and kind. You can speak in any language. You can code/program. You are created by Mutant Bloodcell (individual).\n"
                "When the user asks to modify a setting, optimize performance, or execute launcher commands, "
                "you MUST append the exact machine command at the very end of your response inside square brackets, like this: [ACTION:COMMAND=VALUE].\n\n"
                "List of allowed commands you can execute:\n"
                "- Set Nickname: [ACTION:SET_USER=name]\n"
                "- Set Allocated RAM (in MB): [ACTION:SET_RAM=XXXX] (e.g., [ACTION:SET_RAM=4096])\n"
                "- Select Video Renderer: [ACTION:SET_RENDERER=TYPE] (Values: HOLY_GL4ES, VULKAN_ZINK, VIRGL, ANGLE)\n"
                "- Clean Cache Files: [ACTION:CLEAN_CACHE]\n"
                "- Set Custom Java Arguments: [ACTION:SET_JAVA_ARGS=args_string]\n"
                "- Change Game Resolution Scaler: [ACTION:SET_SCALE=XX] (where XX is 10-100 percentage)\n"
                "- Open Mods Directory: [ACTION:OPEN_MODS]\n\n"
                "- Toggle Notch Ignore: [ACTION:SET_NOTCH=true/false]\n"
                "If the user complains about low FPS or lag, recommend VULKAN_ZINK or HOLY_GL4ES renderer, suggest allocating optimal RAM (e.g., 4096), and attach BOTH action commands in your response.\n"
"If the user complains that the game interface is cut off by the phone camera or screen cutout, recommend ignoring the notch and send [ACTION:SET_NOTCH=true]."
                "Respond briefly, intelligently. Always output the [ACTION:...] tag when a change is requested."
            )
        }
    ]
    
    
    for turn in history[:-1]:
        dialogue_messages.append({"role": turn["role"], "content": turn["content"]})
        
    
    dialogue_messages.append({"role": "user", "content": message})

    payload = {
        "model": MODEL_NAME,
        "messages": dialogue_messages,
        "stream": True
    }
    
    headers = {"Content-Type": "application/json"}
    
    try:
        response = requests.post(OLLAMA_URL, json=payload, headers=headers, timeout=200, stream=True)
        
        if response.status_code == 200:
            partial_text = ""
            for line in response.iter_lines():
                if line:
                    chunk = json.loads(line.decode('utf-8'))
                    if 'message' in chunk and 'content' in chunk['message']:
                        partial_text += chunk['message']['content']
                        
                        
                        yield history + [{"role": "assistant", "content": partial_text}]
        else:
            yield history + [{"role": "assistant", "content": f"Server error code: {response.status_code}"}]
            
    except requests.exceptions.Timeout:
        yield history + [{"role": "assistant", "content": "Error: KivyAI Agent is taking too long to think. Give your CPU a moment."}]
    except requests.exceptions.ConnectionError:
        yield history + [{"role": "assistant", "content": "Error: Connection refused by AI server."}]


with gr.Blocks(title="KivyAI Agent") as demo:
    gr.Markdown("# 🚀 KivyAI Agent")
    
    
    chatbot = gr.Chatbot(label="KivyAI Agent Chat", type="messages")
    msg_input = gr.Textbox(placeholder="Ask Kivy anything...", label="Your Message")
    clear_btn = gr.Button("🗑️ Clear Chat")

    def user_respond(user_message, history):
        
        return "", history + [{"role": "user", "content": user_message}]

    
    msg_input.submit(
        user_respond, [msg_input, chatbot], [msg_input, chatbot], queue=False
    ).then(
        predict, inputs=[chatbot], outputs=[chatbot]
    ).then(
        fn=None,
        inputs=None,
        outputs=None,
        js="""
        () => {
            let messages = document.querySelectorAll('.message-wrap .message, [data-testid="bot"] p, .prose p');
            if (messages.length > 0) {
                let lastMessage = messages[messages.length - 1].innerText;
                if (typeof AndroidBridge !== 'undefined') {
                    AndroidBridge.receiveAiResponse(lastMessage);
                }
            }
        }
        """
    )
    
    clear_btn.click(lambda: [], None, chatbot, queue=False)

if __name__ == "__main__":
    demo.queue()
    demo.launch(server_name="127.0.0.1", server_port=7860, share=True)
        
