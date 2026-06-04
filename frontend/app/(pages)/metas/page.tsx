/**
 * @file app/(pages)/metas/page.tsx
 * @description Pantalla de metas de ahorro
 */
'use client';

import { useState, useEffect } from 'react';
import { Target, Plus, TrendingUp, Trash2 } from 'lucide-react';
import { 
  Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogTrigger 
} from '@/components/ui/dialog';

function FormularioMeta({ onGuardar }: { onGuardar: () => void }) {
  const [open, setOpen] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    formData.append('accion', 'insertar');
    formData.append('idUsuario', '1'); // Ajustar según ID real de sesión

    try {
      const res = await fetch('http://localhost:8080/FinanziApp/api/meta', {
        method: 'POST',
        body: new URLSearchParams(formData as any),
      });
      const data = await res.json();
      if (data.estado) {
        setOpen(false);
        onGuardar();
      } else {
        alert(data.mensaje || "Error al guardar");
      }
    } catch (error) {
      alert("Error de conexión");
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <button className="bg-primary text-primary-foreground px-4 py-2 rounded-lg font-semibold flex items-center gap-2 hover:bg-primary/90">
          <Plus className="h-4 w-4" /> Nueva Meta
        </button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Registrar Nueva Meta</DialogTitle>
          <DialogDescription>
            Completa los datos para definir tu nuevo objetivo de ahorro.
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <label className="text-sm font-medium">Nombre de la meta</label>
            <input name="nombreMeta" required className="w-full p-2 border rounded-md" />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <label className="text-sm font-medium">Monto Objetivo</label>
              <input name="montoMeta" type="number" step="0.01" required className="w-full p-2 border rounded-md" />
            </div>
            <div className="space-y-2">
              <label className="text-sm font-medium">Monto Actual</label>
              <input name="montoActual" type="number" step="0.01" required className="w-full p-2 border rounded-md" />
            </div>
          </div>
          <div className="space-y-2">
            <label className="text-sm font-medium">Fecha Límite</label>
            <input name="fechaLimite" type="date" required className="w-full p-2 border rounded-md" />
          </div>
          <button type="submit" className="w-full bg-primary text-primary-foreground py-2 rounded-lg font-semibold">
            Guardar Meta
          </button>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default function MetasPage() {
  const [metas, setMetas] = useState([]);

  const cargarMetas = async () => {
    try {
      const res = await fetch('http://localhost:8080/FinanziApp/api/meta');
      const data = await res.json();
      setMetas(data.estado && data.lista ? data.lista : []);
    } catch (error) {
      console.error("Error cargando metas:", error);
      setMetas([]);
    }
  };

  const eliminarMeta = async (id: number) => {
    if (!confirm("¿Eliminar esta meta?")) return;
    const params = new URLSearchParams({ accion: 'eliminar', idMeta: id.toString() });
    await fetch('http://localhost:8080/FinanziApp/api/meta', { method: 'POST', body: params });
    cargarMetas();
  };

  useEffect(() => { cargarMetas(); }, []);

  return (
    <div className="p-6 space-y-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center border-b pb-5">
        <h1 className="text-3xl font-bold flex items-center gap-2">
          <Target className="h-8 w-8 text-primary" /> Metas de Ahorro
        </h1>
        <FormularioMeta onGuardar={cargarMetas} />
      </div>

      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {Array.isArray(metas) && metas.map((meta: any) => {
          const progreso = Math.round((meta.montoActual / meta.montoMeta) * 100);
          return (
            <div key={meta.idMeta} className="border bg-card p-6 rounded-xl shadow-sm hover:shadow-md transition-all">
              <div className="flex justify-between items-start">
                <div>
                  <h3 className="font-semibold text-lg">{meta.nombreMeta}</h3>
                  <p className="text-sm text-muted-foreground flex items-center gap-1">
                    <TrendingUp className="h-3.5 w-3.5" /> {progreso}% Completado
                  </p>
                </div>
                <button onClick={() => eliminarMeta(meta.idMeta)} className="text-destructive hover:bg-destructive/10 p-2 rounded-lg">
                  <Trash2 className="h-4 w-4" />
                </button>
              </div>

              <div className="mt-6 space-y-2">
                <div className="w-full bg-muted rounded-full h-2">
                  <div className="bg-primary h-2 rounded-full transition-all" style={{ width: `${Math.min(progreso, 100)}%` }} />
                </div>
                <div className="flex justify-between text-sm">
                  <span className="font-medium">${meta.montoActual.toLocaleString()}</span>
                  <span className="text-muted-foreground">de ${meta.montoMeta.toLocaleString()}</span>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}