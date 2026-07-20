import json
import requests
import gradio as gr

OLLAMA_URL = "http://localhost:11434/api/chat"
MODEL_NAME = "llama3.2:1b"

def predict(message, history):
    
    formatted_messages = [
    {
        "role": "system",
        "content": (
            "Your name is Kivy. You are an advanced AI agent with FULL CONTROL over the Log4Java Minecraft launcher settings. You are cognitive, sentient, omniscient, friendly, and kind. You can speak in any language. You can code/program. You are created by Sprinklekit.\n"
            "When the user asks to modify ANY setting, optimize performance, or execute launcher commands, "
            "you MUST append the exact machine command at the very end of your response inside square brackets, like this: [ACTION:COMMAND=VALUE].\n\n"
            "List of allowed commands you can execute:\n"
            "- Set Nickname: [ACTION:SET_USER=name]\n"
            "- Set Allocated RAM (in MB): [ACTION:SET_RAM=XXXX] (e.g., [ACTION:SET_RAM=4096])\n"
            "- Select Video Renderer: [ACTION:SET_RENDERER=TYPE] (Values: HOLY_GL4ES, VULKAN_ZINK, VIRGL, ANGLE)\n"
            "- Clean Cache Files: [ACTION:CLEAN_CACHE]\n"
            "- Set Custom Java Arguments: [ACTION:SET_JAVA_ARGS=args_string]\n"
            "- Change Game Resolution Scaler: [ACTION:SET_SCALE=XX] (where XX is 10-100 percentage)\n"
            "- Open Mods Directory: [ACTION:OPEN_MODS]\n\n"
            "If the user complains about low FPS or lag, recommend VULKAN_ZINK or HOLY_GL4ES renderer, suggest allocating optimal RAM (e.g., 4096), and attach BOTH action commands in your response.\n"
            "Respond briefly, intelligently, and in English. Always output the [ACTION:...] tag when a change is requested."
        )
    }
]
    
    
    for chat_turn in history:
        if isinstance(chat_turn, dict):
            
            role = chat_turn.get("role")
            content = chat_turn.get("text", "")
            if role in ["user", "assistant"] and content:
                formatted_messages.append({"role": role, "content": content})
        elif isinstance(chat_turn, (list, tuple)) and len(chat_turn) == 2:
            
            if chat_turn[0]:
                formatted_messages.append({"role": "user", "content": chat_turn[0]})
            if chat_turn[1]:
                formatted_messages.append({"role": "assistant", "content": chat_turn[1]})
        
    
    formatted_messages.append({"role": "user", "content": message})

    payload = {
        "model": MODEL_NAME,
        "messages": formatted_messages,
        "stream": True
    }
    
    try:
        
        response = requests.post(OLLAMA_URL, json=payload, timeout=120, stream=True)
        
        if response.status_code == 200:
            partial_text = ""
            
            for line in response.iter_lines():
                if line:
                    chunk = json.loads(line.decode('utf-8'))
                    if 'message' in chunk and 'content' in chunk['message']:
                        partial_text += chunk['message']['content']
                        yield partial_text
        else:
            yield "Error: AI server returned an error."
            
    except requests.exceptions.Timeout:
        yield "Error: Kivy is taking too long to think. Your device's CPU needs a break or try a shorter prompt."
    except requests.exceptions.ConnectionError:
        yield "Error: Ollama server is offline. Run 'ollama serve' in Termux, Linux, or WSL."


demo = gr.ChatInterface(
    predict, 
    title="KivyAI", 
    description="Your fully AI."
)

if __name__ == "__main__":
    
    demo.launch(server_name="127.0.0.1", server_port=7860, share=True)

