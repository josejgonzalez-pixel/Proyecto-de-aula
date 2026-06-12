'use client';

import { useState, useEffect } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus, Tag, Trash2 } from 'lucide-react';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';

export default function CategoriasPage() {
  const [categorias, setCategorias] = useState([]);
  const [open, setOpen] = useState(false);
  const [nombre, setNombre] = useState('');
  const [tipo, setTipo] = useState('Gasto');

  const cargarCategorias = () => {
    fetch('http://localhost:8080/FinanziApp/api/categorias')
      .then(res => res.json())
      .then(data => setCategorias(data.lista || []));
  };

  useEffect(() => { cargarCategorias(); }, []);

  const registrarCategoria = async (e: React.FormEvent) => {
    e.preventDefault();
    await fetch('http://localhost:8080/FinanziApp/api/categorias', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nombreCategoria: nombre, tipo })
    });
    setOpen(false);
    setNombre('');
    cargarCategorias();
  };

  const eliminarCategoria = async (id: number) => {
    if (!confirm("¿Eliminar esta categoría?")) return;

    // Usamos URLSearchParams para asegurar que llegue como parámetros de formulario
    const params = new URLSearchParams();
    params.append('accion', 'eliminar');
    params.append('id', id.toString());

    const response = await fetch('http://localhost:8080/FinanziApp/api/categorias', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString() // <--- IMPORTANTE: convertimos a string
    });

    if (response.ok) {
      cargarCategorias(); // Recargamos si todo fue OK
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Categorías</h1>
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <Button><Plus className="mr-2 h-4 w-4" /> Nueva Categoría</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader><DialogTitle>Nueva Categoría</DialogTitle></DialogHeader>
            <form onSubmit={registrarCategoria} className="space-y-4">
              <Label>Nombre</Label>
              <Input value={nombre} onChange={e => setNombre(e.target.value)} required />
              <Label>Tipo</Label>
              <select className="w-full border p-2 rounded" value={tipo} onChange={e => setTipo(e.target.value)}>
                <option>Gasto</option>
                <option>Ingreso</option>
              </select>
              <Button className="w-full">Guardar</Button>
            </form>
          </DialogContent>
        </Dialog>
      </div>

      <div className="grid gap-4 md:grid-cols-3">
        {categorias.map((cat: any) => (
          <Card key={cat.idCategoria} className="relative">
            <Button variant="ghost" size="icon" className="absolute top-2 right-2 text-destructive" onClick={() => eliminarCategoria(cat.idCategoria)}>
              <Trash2 className="h-4 w-4" />
            </Button>
            <CardHeader className="flex flex-row items-center gap-2">
              <Tag className="h-4 w-4 text-primary" />
              <CardTitle className="text-base">{cat.nombreCategoria}</CardTitle>
            </CardHeader>
            <CardContent><p className="text-sm text-muted-foreground">Tipo: {cat.tipo}</p></CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}