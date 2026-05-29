/**
 * @file app/metas/page.tsx
 * @description Vista de prueba para el apartado de Metas de Ahorro
 */

'use client';

import { Target, Plus, TrendingUp } from 'lucide-react';

export default function MetasPage() {
  // Datos dummy locales para probar la interfaz visual
  const metasSimuladas = [
    { id: 1, titulo: 'Fondo de Emergencias', actual: 1200000, objetivo: 3000000, porcentaje: 40 },
    { id: 2, titulo: 'Inversión en Hardware / RAM', actual: 450000, objetivo: 600000, porcentaje: 75 },
    { id: 3, titulo: 'Próximo Viaje', actual: 300000, objetivo: 2000000, porcentaje: 15 },
  ];

  return (
    <div className="p-6 space-y-6 max-w-7xl mx-auto">
      
      {/* Encabezado de la página */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4 border-b pb-5">
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-foreground flex items-center gap-2">
            <Target className="h-8 w-8 text-primary" />
            Metas de Ahorro
          </h1>
          <p className="text-muted-foreground mt-1">
            Gestiona tus objetivos financieros a corto y largo plazo.
          </p>
        </div>
        
        {/* Botón de acción */}
        <button className="inline-flex items-center justify-center gap-2 rounded-lg bg-primary px-4 py-2.5 text-sm font-semibold text-primary-foreground shadow-sm hover:bg-primary/90 transition-colors self-start sm:self-auto">
          <Plus className="h-4 w-4" />
          Nueva Meta
        </button>
      </div>

      {/* Grid de Tarjetas de Metas */}
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {metasSimuladas.map((meta) => (
          <div key={meta.id} className="rounded-xl border bg-card p-6 shadow-sm hover:shadow-md transition-shadow">
            <div className="flex items-start justify-between">
              <div className="space-y-1">
                <h3 className="font-semibold text-lg text-foreground tracking-tight">{meta.titulo}</h3>
                <p className="text-sm text-muted-foreground flex items-center gap-1">
                  <TrendingUp className="h-3.5 w-3.5 text-primary" />
                  {meta.porcentaje}% Completado
                </p>
              </div>
              <span className="p-2 rounded-lg bg-primary/10 text-primary">
                <Target className="h-5 w-5" />
              </span>
            </div>

            {/* Barra de progreso */}
            <div className="mt-6 space-y-2">
              <div className="w-full bg-muted rounded-full h-2">
                <div 
                  className="bg-primary h-2 rounded-full transition-all duration-500" 
                  style={{ width: `${meta.porcentaje}%` }}
                />
              </div>
              
              {/* Valores monetarios */}
              <div className="flex justify-between text-sm mt-1">
                <span className="font-medium text-foreground">
                  ${meta.actual.toLocaleString('es-CO')} COP
                </span>
                <span className="text-muted-foreground">
                  de ${meta.objetivo.toLocaleString('es-CO')}
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>

    </div>
  );
}