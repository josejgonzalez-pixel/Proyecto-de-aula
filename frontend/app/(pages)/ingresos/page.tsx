/**
 * @file app/(pages)/cuentas/page.tsx
 * @description Página de gestión de ingresos financieros conectada al backend de Java
 */

'use client';

import { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus, ArrowUpRight, Calendar, Tag, Loader2, RefreshCw } from 'lucide-react';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger, DialogFooter } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';

// Estructura identica al objeto Ingreso/transacción de la base de datos
interface Ingreso {
  idIngreso: number;
  descripcion: string;
  monto: number;
  fecha: string; // ISO string
  categoriaNombre?: string;
  idUsuario: number;
}

export default function IngresosPage() {
  const [ingresos, setIngresos] = useState<Ingreso[]>([]);
  const [cargando, setCargando] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [modalAbierto, setModalAbierto] = useState(false);
  const [guardando, setGuardando] = useState(false);
  const [formDescripcion, setFormDescripcion] = useState('');
  const [formMonto, setFormMonto] = useState('');
  const [formFecha, setFormFecha] = useState(new Date().toISOString().split('T')[0]);

  // 2. Función encargada de consumir el Servlet de Java
  const cargarIngresos = async () => {
    setCargando(true);
    setError(null);
    try {
      // Apuntamos al endpoint que mapearemos en el backend para listar ingresos
      const response = await fetch('http://localhost:8080/FinanziApp/ingresos', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('El servidor backend no respondió correctamente.');
      }

      const data = await response.json();

      // Validamos la respuesta estructurada de tu clase Util.Response
      if (data.estado) {
        setIngresos(data.lista || []);
      } else {
        setError(data.mensaje || 'Error al procesar los ingresos.');
      }
    } catch (err: any) {
      setError(err.message || 'Error de comunicación con Tomcat.');
    } finally {
      setCargando(false);
    }
  };

  // 3. Cargar los datos automáticamente al montar la vista
  useEffect(() => {
    cargarIngresos();
  }, []);

  // 4. Cálculo dinámico del total de ingresos acumulados
  const totalIngresos = ingresos.reduce((acumulado, item) => acumulado + item.monto, 0);

  
  const handleGuardarIngreso = async (e: React.FormEvent) => {
    e.preventDefault();
    setGuardando(true);
    
    const params = new URLSearchParams();
    params.append('descripcion', formDescripcion);
    params.append('monto', formMonto);
    params.append('fecha', formFecha);

    try {
      const response = await fetch('http://localhost:8080/FinanziApp/ingresos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString(),
      });
      
      const data = await response.json();
      if (data.estado) {
        setModalAbierto(false);
        cargarIngresos();
      }
    } catch (err) {
      alert("Error al guardar");
    } finally {
      setGuardando(false);
    }
  };

  return (
    <div className="space-y-6">
      {/* Cabecera Principal */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Ingresos</h1>
          <p className="text-sm text-muted-foreground">Monitorea y registra tus fuentes de ganancias</p>
        </div>
        <div className="flex gap-2">
          <Button variant="outline" size="icon" onClick={cargarIngresos} disabled={cargando}>
            <RefreshCw className={`h-4 w-4 ${cargando ? 'animate-spin' : ''}`} />
          </Button>
          <Dialog open={modalAbierto} onOpenChange={setModalAbierto}>
            <DialogTrigger asChild>
              <Button>
                <Plus className="h-4 w-4" />
                Nuevo Ingreso
              </Button>
            </DialogTrigger>

            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>Registrar Nuevo Ingreso</DialogTitle>
              </DialogHeader>
              {/* El formulario que conecta con la lógica de Java */}
              <form onSubmit={handleGuardarIngreso} className="space-y-4 py-4">
                <div className="space-y-2">
                  <Label htmlFor="descripcion">Descripción</Label>
                  <Input
                    id="descripcion"
                    placeholder="Ej. Salario, Venta, etc."
                    value={formDescripcion}
                    onChange={(e) => setFormDescripcion(e.target.value)}
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="monto">Monto (COP)</Label>
                  <Input
                    id="monto"
                    type="number"
                    placeholder="0"
                    value={formMonto}
                    onChange={(e) => setFormMonto(e.target.value)}
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="fecha">Fecha</Label>
                  <Input
                    id="fecha"
                    type="date"
                    value={formFecha}
                    onChange={(e) => setFormFecha(e.target.value)}
                    required
                  />
                </div>

                <DialogFooter className="pt-4">
                  <Button type="button" variant="outline" onClick={() => setModalAbierto(false)}>
                    Cancelar
                  </Button>
                  <Button type="submit" disabled={guardando}>
                    {guardando ? 'Guardando...' : 'Guardar Ingreso'}
                  </Button>
                </DialogFooter>
              </form>
            </DialogContent>
          </Dialog>
        </div>
      </div>

      {/* Tarjeta de Resumen General */}
      {!cargando && !error && (
        <Card className="bg-primary/5 border-primary/20">
          <CardContent className="p-6 flex items-center justify-between">
            <div className="space-y-1">
              <p className="text-sm font-medium text-muted-foreground">Total Ingresado</p>
              <p className="text-3xl font-bold text-primary">
                ${totalIngresos.toLocaleString('es-CO')} COP
              </p>
            </div>
            <div className="p-3 bg-primary text-white rounded-full">
              <ArrowUpRight className="h-6 w-6" />
            </div>
          </CardContent>
        </Card>
      )}

      {/* ⏳ ESTADO: Cargando datos */}
      {cargando && (
        <div className="flex flex-col items-center justify-center py-16 space-y-4">
          <Loader2 className="h-8 w-8 animate-spin text-primary" />
          <p className="text-sm text-muted-foreground">Leyendo el histórico de ingresos...</p>
        </div>
      )}

      {/* ESTADO: Captura de Errores */}
      {error && (
        <div className="rounded-lg bg-destructive/10 p-4 text-sm text-destructive space-y-2">
          <p className="font-semibold">Ocurrió un inconveniente de conexión:</p>
          <p>{error}</p>
        </div>
      )}

      {/* 📭 ESTADO: Lista vacía en la Base de Datos */}
      {!cargando && !error && ingresos.length === 0 && (
        <div className="text-center py-16 border border-dashed rounded-lg bg-muted/20">
          <p className="text-sm text-muted-foreground">Aún no has registrado ningún ingreso en FinanziApp.</p>
        </div>
      )}

      {/* 📊 DESPLIEGUE DINÁMICO DESDE MYSQL */}
      {!cargando && !error && ingresos.length > 0 && (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {ingresos.map((ingreso) => (
            <Card key={ingreso.idIngreso} className="transition-all hover:shadow-md">
              <CardHeader className="pb-2">
                <div className="flex items-start justify-between">
                  <div className="space-y-1">
                    <CardTitle className="text-base font-semibold">{ingreso.descripcion}</CardTitle>
                    <div className="flex flex-wrap gap-2 text-xs text-muted-foreground">
                      <span className="flex items-center gap-1">
                        <Calendar className="h-3 w-3" />
                        {ingreso.fecha}
                      </span>
                      {ingreso.categoriaNombre && (
                        <span className="flex items-center gap-1 bg-secondary px-2 py-0.5 rounded text-secondary-foreground font-medium">
                          <Tag className="h-3 w-3" />
                          {ingreso.categoriaNombre}
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              </CardHeader>
              <CardContent>
                <p className="text-2xl font-bold text-emerald-600 dark:text-emerald-400">
                  + ${ingreso.monto.toLocaleString('es-CO')} COP
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}