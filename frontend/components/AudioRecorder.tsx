import { useState, useRef } from 'react';
import RecordRTC, { StereoAudioRecorder } from 'recordrtc';

const AudioRecorder = () => {
  const [recording, setRecording] = useState(false);
  const recorderRef = useRef(null); // Aquí guardaremos la instancia de RecordRTC

  const startRecording = async () => {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    
    // Configuración para que Vosk lo acepte sin problemas
    const recorder = new RecordRTC(stream, {
      type: 'audio',
      mimeType: 'audio/wav',
      recorderType: StereoAudioRecorder,
      numberOfAudioChannels: 1, // Mono
      desiredSampRate: 16000,    // 16kHz
    });

    recorder.startRecording();
    recorderRef.current = recorder;
    setRecording(true);
  };

  const stopRecording = () => {
    const recorder = recorderRef.current;
    recorder.stopRecording(() => {
      const blob = recorder.getBlob();
      sendToBackend(blob);
      setRecording(false);
      
      // Limpiar el stream (apagar el micrófono)
      recorder.stream.getTracks().forEach(track => track.stop());
    });
  };

  const sendToBackend = async (blob) => {
    const formData = new FormData();
    formData.append('file', blob, 'audio.wav');

    try {
      const response = await fetch('http://localhost:8080/TuProyecto/TuServlet', {
        method: 'POST',
        body: formData,
      });
      const result = await response.json();
      console.log("Respuesta del servidor:", result);
    } catch (error) {
      console.error("Error enviando el audio:", error);
    }
  };

  return (
    <button onClick={recording ? stopRecording : startRecording}>
      {recording ? "Detener y Enviar" : "Grabar Audio"}
    </button>
  );
};

export default AudioRecorder;