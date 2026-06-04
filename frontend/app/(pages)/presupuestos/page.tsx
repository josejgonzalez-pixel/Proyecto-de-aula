/**
 * @file app/(pages)/presupuestos/page.tsx
 * @description Pantalla de presupuestos
 */
'use client';

import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus } from 'lucide-react';
import { Progress } from '@/components/ui/progress';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger, DialogDescription } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';

interface Presupuesto {
  idPresupuesto: number;
  montoInicial: number;
  montoActual: number;
  fechaCreacion: string;
  idUsuario: number;
}

export default function PresupuestosPage() {
  const [presupuestos, setPresupuestos] = useState<Presupuesto[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [montoInicial, setMontoInicial] = useState('');
  const [open, setOpen] = useState(false);
  const [fecha, setFecha] = useState('');
  const [montoActual, setMontoActual] = useState('');
  const [presupuestoEditando, setPresupuestoEditando] = useState<Presupuesto | null>(null);

  const cargarPresupuestos = async () => {
    setIsLoading(true);
    try {
      const response = await fetch('http://localhost:8080/FinanziApp/presupuesto');
      const data = await response.json();
      if (data.estado) setPresupuestos(data.lista || []);
    } catch (err) {
      console.error("Error al cargar:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const abrirEdicion = (p: Presupuesto) => {
    setPresupuestoEditando(p);
    setMontoInicial(p.montoInicial.toString());
    setMontoActual(p.montoActual.toString());
    setFecha(p.fechaCreacion);
    setOpen(true);
  };

  const eliminarPresupuesto = async (id: number) => {
    if (!confirm("¿Estás seguro?")) return;

    const params = new URLSearchParams();
    params.append('idPresupuesto', id.toString());
    params.append('accion', 'eliminar');

    try {
      const response = await fetch('http://localhost:8080/FinanziApp/presupuesto', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params // <--- Si esto falla, intenta con params.toString()
      });
      const data = await response.json();
      if (data.estado) cargarPresupuestos();
    } catch (err) {
      console.error("Error:", err);
    }
  };

  const registrarPresupuesto = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new URLSearchParams();
    formData.append('fechaCreacion', fecha);
    formData.append('montoInicial', montoInicial);
    formData.append('montoActual', montoActual);
    formData.append('idUsuario', '1');

    let url = 'http://localhost:8080/FinanziApp/presupuesto';
    if (presupuestoEditando) {
      formData.append('idPresupuesto', presupuestoEditando.idPresupuesto.toString());
      url += '?accion=actualizar';
    }

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'content-type': 'application/x-www-form-urlencoded' },
        body: formData
      });
      const data = await response.json();
      if (data.estado) {
        setOpen(false);
        setMontoInicial(''); setMontoActual(''); setFecha(''); setPresupuestoEditando(null);
        cargarPresupuestos();
      }
    } catch (err) {
      console.error("Error al guardar:", err);
    }
  };

  useEffect(() => { cargarPresupuestos(); }, []);

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">Presupuestos</h1>
        <Dialog open={open} onOpenChange={(v) => { setOpen(v); if (!v) setPresupuestoEditando(null); }}>
          <DialogTrigger asChild>
            <Button><Plus className="mr-2 h-4 w-4" /> Nuevo Presupuesto</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>{presupuestoEditando ? "Editar" : "Registrar"} Presupuesto</DialogTitle>
              <DialogDescription>
                completa el formulario a continuación para {presupuestoEditando ? "actualizar" : "crear"} tu presupuesto.
              </DialogDescription>
            </DialogHeader>
            <form onSubmit={registrarPresupuesto} className="space-y-4">
              <Label>Monto Inicial</Label>
              <Input type="number" value={montoInicial} onChange={(e) => setMontoInicial(e.target.value)} required />
              <Label>Monto Actual</Label>
              <Input type="number" value={montoActual} onChange={(e) => setMontoActual(e.target.value)} required />
              <Label>Fecha</Label>
              <Input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} required />
              <Button type="submit" className="w-full">Guardar</Button>
            </form>
          </DialogContent>
        </Dialog>
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        {isLoading ? (
          <p>Cargando...</p>
        ) : presupuestos.length === 0 ? (
          <p>No hay presupuestos.</p>
        ) : (
          presupuestos.map((p) => {
            const percentage = Math.round((p.montoActual / p.montoInicial) * 100);
            return (
              <Card key={p.idPresupuesto}>
                <CardHeader className="flex flex-row justify-between items-center">
                  <CardTitle className="text-base">{p.fechaCreacion}</CardTitle>

                  {/* Contenedor para ambos botones */}
                  <div className="flex gap-2">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => abrirEdicion(p)}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="destructive"
                      size="sm"
                      onClick={() => eliminarPresupuesto(p.idPresupuesto)}
                    >
                      Eliminar
                    </Button>
                  </div>
                </CardHeader>

                <CardContent className="space-y-2">
                  <Progress value={percentage} />

                  {/* Este contenedor muestra ambos montos */}
                  <div className="flex justify-between text-sm">
                    <span className="text-muted-foreground">
                      Gastado: ${p.montoActual.toLocaleString('es-CO')}
                    </span>
                    <span className="font-medium text-foreground">
                      límite: ${p.montoInicial.toLocaleString('es-CO')}
                    </span>
                  </div>
                </CardContent>
              </Card>
            );
          })
        )}
      </div>
    </div>
  );
}