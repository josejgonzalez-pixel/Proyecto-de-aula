'use client';

import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus } from 'lucide-react';
import { Progress } from '@/components/ui/progress';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { useAuth } from '@/context/AuthContext';

interface Presupuesto {
  idPresupuesto: number;
  nombre: string;
  montoInicial: number;
  montoActual: number;
  fechaCreacion: string;
  idUsuario: number;
}

export default function PresupuestosPage() {
  const [presupuestos, setPresupuestos] = useState<Presupuesto[]>([]);
  const [open, setOpen] = useState(false);
  const [nombre, setNombre] = useState('');
  const [montoInicial, setMontoInicial] = useState('');
  const [montoActual, setMontoActual] = useState('');
  const [fecha, setFecha] = useState('');
  const [presupuestoEditando, setPresupuestoEditando] = useState<Presupuesto | null>(null);

  const { userId } = useAuth();

  const cargarPresupuestos = async () => {
    try {
      const response = await fetch('http://localhost:8080/FinanziApp/presupuesto');
      const data = await response.json();
      if (data.estado) setPresupuestos(data.lista || []);
    } catch (err) { console.error("Error al cargar:", err); }
  };

  const registrarPresupuesto = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!userId) { alert("Usuario no identificado. Inicia sesión."); return; }

    const formData = new URLSearchParams();
    formData.append('nombre', nombre);
    formData.append('fechaCreacion', fecha);
    formData.append('montoInicial', montoInicial);
    formData.append('montoActual', montoActual);
    formData.append('idUsuario', userId.toString());

    if (presupuestoEditando) {
      formData.append('idPresupuesto', presupuestoEditando.idPresupuesto.toString());
      formData.append('accion', 'actualizar');
    } else {
      formData.append('accion', 'insertar');
    }

    const response = await fetch('http://localhost:8080/FinanziApp/presupuesto', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: formData.toString()
    });
    const data = await response.json();

    if (data.estado) {
      setOpen(false);
      setNombre(''); setMontoInicial(''); setMontoActual(''); setFecha('');
      setPresupuestoEditando(null);
      cargarPresupuestos();
    } else {
      alert("Error del servidor: " + data.mensaje);
    }
  };

  const eliminarPresupuesto = async (id: number) => {
    if (!confirm("¿Eliminar?")) return;
    const params = new URLSearchParams();
    params.append('idPresupuesto', id.toString());
    params.append('accion', 'eliminar');
    await fetch('http://localhost:8080/FinanziApp/presupuesto', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    });
    cargarPresupuestos();
  };

  useEffect(() => { cargarPresupuestos(); }, []);

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Presupuestos</h1>
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild><Button><Plus className="mr-2 h-4 w-4" /> Nuevo presupuesto</Button></DialogTrigger>
          <DialogContent>
            {/* AGREGA ESTO PARA QUITAR EL ERROR */}
            <DialogHeader>
              <DialogTitle>
                {presupuestoEditando ? "Editar Presupuesto" : "Nuevo Presupuesto"}
              </DialogTitle>
            </DialogHeader>

            {/* TU FORMULARIO AQUÍ */}
            <form onSubmit={registrarPresupuesto} className="space-y-4">
              <Label>Nombre</Label>
              <Input value={nombre} onChange={e => setNombre(e.target.value)} required />
              <Label>Monto Inicial</Label>
              <Input type="number" value={montoInicial} onChange={e => setMontoInicial(e.target.value)} required />
              <Label>Monto Actual</Label>
              <Input type="number" value={montoActual} onChange={e => setMontoActual(e.target.value)} required />
              <Label>Fecha</Label>
              <Input type="date" value={fecha} onChange={e => setFecha(e.target.value)} required />
              <Button type="submit" className="w-full">Guardar</Button>
            </form>
          </DialogContent>
        </Dialog>
      </div>
      <div className="grid gap-4 md:grid-cols-2">
        {presupuestos.map((p) => (
          <Card key={p.idPresupuesto}>
            <CardHeader className="flex flex-row justify-between items-center">
              <CardTitle>{p.nombre}</CardTitle>
              <div className="flex gap-2">
                <Button variant="outline" size="sm" onClick={() => {
                  setPresupuestoEditando(p);
                  setNombre(p.nombre);
                  setMontoInicial(p.montoInicial.toString());
                  setMontoActual(p.montoActual.toString());
                  setFecha(p.fechaCreacion);
                  setOpen(true);
                }}>Editar</Button>
                <Button variant="destructive" size="sm" onClick={() => eliminarPresupuesto(p.idPresupuesto)}>Eliminar</Button>
              </div>
            </CardHeader>

            {/* AQUÍ AGREGAMOS LOS CAMPOS QUE FALTAN */}
            <CardContent className="space-y-2">
              <p className="text-sm text-muted-foreground">Fecha: {p.fechaCreacion}</p>
              <div className="flex justify-between text-sm">
                <span>Monto Inicial:</span>
                <span className="font-semibold">${p.montoInicial}</span>
              </div>
              <div className="flex justify-between text-sm">
                <span>Monto Actual:</span>
                <span className="font-semibold text-emerald-600">${p.montoActual}</span>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}