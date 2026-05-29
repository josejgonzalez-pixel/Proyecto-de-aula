/**
 * @file components/dashboard/quick-add-form.tsx
 * @description Formulario rápido para agregar transacciones
 * Permite registrar ingresos y gastos rápidamente
 */

'use client';

import { useState } from 'react';
import { Plus, X } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Textarea } from '@/components/ui/textarea';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { categories } from '@/lib/data';

/**
 * Formulario de registro de transacción
 * Usado tanto en el modal mobile como en el sidebar desktop
 */
function TransactionForm({ onClose }: { onClose?: () => void }) {
  const [monto, setMonto] = useState('');
  const [categoria, setCategoria] = useState('');
  const [fecha, setFecha] = useState('');
  const [comentarios, setComentarios] = useState('');
  
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Aquí iría la lógica para guardar la transacción
    console.log({ monto, categoria, fecha, comentarios });
    // Reset form
    setMonto('');
    setCategoria('');
    setFecha('');
    setComentarios('');
    onClose?.();
  };
  
  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="monto" className="text-xs">Monto</Label>
          <Input
            id="monto"
            type="number"
            placeholder="$"
            value={monto}
            onChange={(e) => setMonto(e.target.value)}
            className="h-9"
          />
        </div>
        <div className="space-y-2">
          <Label htmlFor="categoria-select" className="text-xs">Categoría</Label>
          <Select value={categoria} onValueChange={setCategoria}>
            <SelectTrigger id="categoria-select" className="h-9">
              <SelectValue placeholder="Categoría" />
            </SelectTrigger>
            <SelectContent>
              {categories.map((cat) => (
                <SelectItem key={cat} value={cat}>{cat}</SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
      </div>
      
      <div className="grid grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="categoria-type" className="text-xs">Categoría</Label>
          <Select>
            <SelectTrigger id="categoria-type" className="h-9">
              <SelectValue placeholder="Categoría" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="ingreso">Ingreso</SelectItem>
              <SelectItem value="gasto">Gasto</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <div className="space-y-2">
          <Label htmlFor="fecha" className="text-xs">Fecha</Label>
          <Input
            id="fecha"
            type="date"
            value={fecha}
            onChange={(e) => setFecha(e.target.value)}
            className="h-9"
          />
        </div>
      </div>
      
      <div className="space-y-2">
        <Label htmlFor="comentarios" className="text-xs">Comentarios</Label>
        <Textarea
          id="comentarios"
          placeholder="Comentarios"
          value={comentarios}
          onChange={(e) => setComentarios(e.target.value)}
          className="h-20 resize-none"
        />
      </div>
      
      <Button type="submit" className="w-full">
        Guardar Transacción
      </Button>
    </form>
  );
}

/**
 * Botón flotante Quick Add (solo mobile)
 * Abre un modal con el formulario de transacción
 */
export function QuickAddButton() {
  const [open, setOpen] = useState(false);
  
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button
          className="fixed bottom-20 right-4 z-40 h-12 gap-2 rounded-full shadow-lg lg:hidden"
          size="lg"
        >
          <Plus className="h-5 w-5" />
          Quick Add
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Registrar Gasto/Ingreso</DialogTitle>
        </DialogHeader>
        <TransactionForm onClose={() => setOpen(false)} />
      </DialogContent>
    </Dialog>
  );
}

/**
 * Tarjeta de formulario (solo desktop)
 * Muestra el formulario directamente en el sidebar
 */
export function QuickAddCard() {
  return (
    <Card>
      <CardHeader className="pb-2">
        <CardTitle className="text-base font-semibold">
          Registrar Gasto/Ingreso
        </CardTitle>
      </CardHeader>
      <CardContent>
        <TransactionForm />
      </CardContent>
    </Card>
  );
}
