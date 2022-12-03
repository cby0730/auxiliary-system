from gtts import gTTS
from playsound import playsound
def say(text):
    audio = gTTS( text = text, lang = 'en' )
    name = '{}.mp3'.format(text)
    try:
        playsound(name)
    except:
        audio.save(name)
        playsound(name)