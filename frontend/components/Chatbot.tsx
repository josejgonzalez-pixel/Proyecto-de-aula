'use client';
import { useState, useRef, useEffect } from 'react'; // <--- useEffect agregado
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { MessageCircle, X, Mic, MicOff } from 'lucide-react';

export default function Chatbot() {
    const [isOpen, setIsOpen] = useState(false);
    const [mensaje, setMensaje] = useState('');
    const [isRecording, setIsRecording] = useState(false);
    const [historial, setHistorial] = useState<{ remitente: 'user' | 'bot', texto: string }[]>([]);

    const recorderRef = useRef<any>(null);
    const streamRef = useRef<MediaStream | null>(null);
    const scrollRef = useRef<HTMLDivElement>(null); // <--- Referencia para el scroll

    // Efecto para hacer scroll al final cada vez que el historial cambia
    useEffect(() => {
        scrollRef.current?.scrollIntoView({ behavior: 'smooth' });
    }, [historial]);

    const enviarMensaje = async (textoAEnviar?: string) => {
        const textoFinal = textoAEnviar || mensaje;
        if (!textoFinal.trim()) return;

        const nuevoHistorial = [...historial, { remitente: 'user' as const, texto: textoFinal }];
        setHistorial(nuevoHistorial);
        setMensaje('');

        try {
            const res = await fetch('http://localhost:8080/FinanziApp/chatbot', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ mensaje: textoFinal }),
            });
            const data = await res.json();
            setHistorial([...nuevoHistorial, { remitente: 'bot', texto: data.respuesta }]);
        } catch {
            setHistorial([...nuevoHistorial, { remitente: 'bot', texto: 'Error de conexión.' }]);
        }
    };

    const startRecording = async () => {
        const RecordRTC = (await import('recordrtc')).default;
        const { StereoAudioRecorder } = await import('recordrtc');

        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        streamRef.current = stream;

        const recorder = new RecordRTC(stream, {
            type: 'audio',
            mimeType: 'audio/wav',
            recorderType: StereoAudioRecorder,
            numberOfAudioChannels: 1,
            desiredSampRate: 16000,
        });

        recorder.startRecording();
        recorderRef.current = recorder;
        setIsRecording(true);
    };

    const stopRecording = () => {
        if (!recorderRef.current) return;
        recorderRef.current.stopRecording(async () => {
            const blob = recorderRef.current.getBlob();
            setIsRecording(false);
            streamRef.current?.getTracks().forEach((t: any) => t.stop());
            if (blob) {
                const formData = new FormData();
                formData.append('file', blob, 'audio.wav');
                try {
                    const res = await fetch('http://localhost:8080/FinanziApp/chatbot-audio', {
                        method: 'POST',
                        body: formData,
                    });
                    const data = await res.json();
                    setHistorial(prev => [...prev, { remitente: 'bot', texto: data.respuesta }]);
                } catch (e) { console.error("Error al enviar audio", e); }
            }
        });
    };

    return (
        <div className="fixed bottom-4 right-4 z-50">
            {!isOpen ? (
                <Button size="icon" className="h-12 w-12 rounded-full" onClick={() => setIsOpen(true)}>
                    <MessageCircle />
                </Button>
            ) : (
                <Card className="w-80 h-96 flex flex-col shadow-xl">
                    <CardHeader className="flex flex-row justify-between items-center p-4 border-b">
                        <CardTitle className="text-sm">FinanziBot</CardTitle>
                        <X size={16} className="cursor-pointer" onClick={() => setIsOpen(false)} />
                    </CardHeader>

                    <CardContent className="flex-1 overflow-y-auto p-4 space-y-2">
                        {historial.map((msg, i) => (
                            <div key={i} className={`text-xs p-2 rounded-lg ${msg.remitente === 'user' ? 'bg-primary text-primary-foreground ml-auto w-fit' : 'bg-muted w-fit'}`}>
                                {msg.texto}
                            </div>
                        ))}
                        {/* El ancla para el scroll automático */}
                        <div ref={scrollRef} />
                    </CardContent>

                    {/* Sugerencias */}
                    <div className="flex flex-wrap gap-1.5 px-4 pb-2">
                        {["Ver presupuesto", "Ver metas"].map((sug, i) => (
                            <Button key={i} variant="outline" size="sm" className="text-[10px] h-6 px-2 rounded-full" onClick={() => enviarMensaje(sug)}>
                                {sug}
                            </Button>
                        ))}
                    </div>

                    <div className="p-4 border-t flex gap-2">
                        <Input value={mensaje} onChange={(e) => setMensaje(e.target.value)} placeholder="Escribe..." />
                        <Button size="sm" variant={isRecording ? "destructive" : "secondary"} onClick={isRecording ? stopRecording : startRecording}>
                            {isRecording ? <MicOff size={16} /> : <Mic size={16} />}
                        </Button>
                        <Button size="sm" onClick={() => enviarMensaje()}>Enviar</Button>
                    </div>
                </Card>
            )}
        </div>
    );
}