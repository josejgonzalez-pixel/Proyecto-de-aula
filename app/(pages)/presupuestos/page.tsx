/**
 * @file app/(pages)/presupuestos/page.tsx
 * @description Página de gestión de presupuestos mensuales
 */

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus } from 'lucide-react';
import { Progress } from '@/components/ui/progress';

const budgets = [
  { id: 1, category: 'Alimentación', budget: 500000, spent: 425000, color: 'bg-chart-1' },
  { id: 2, category: 'Transporte', budget: 200000, spent: 180000, color: 'bg-chart-3' },
  { id: 3, category: 'Entretenimiento', budget: 150000, spent: 75000, color: 'bg-chart-4' },
  { id: 4, category: 'Servicios', budget: 300000, spent: 280000, color: 'bg-chart-2' },
];

export default function PresupuestosPage() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Presupuestos</h1>
          <p className="text-sm text-muted-foreground">Controla tus gastos por categoría</p>
        </div>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          Nuevo Presupuesto
        </Button>
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        {budgets.map((budget) => {
          const percentage = Math.round((budget.spent / budget.budget) * 100);
          const isOverBudget = percentage > 90;
          return (
            <Card key={budget.id}>
              <CardHeader className="pb-2">
                <div className="flex items-center justify-between">
                  <CardTitle className="text-base">{budget.category}</CardTitle>
                  <span className={`text-sm font-medium ${isOverBudget ? 'text-destructive' : 'text-muted-foreground'}`}>
                    {percentage}%
                  </span>
                </div>
              </CardHeader>
              <CardContent className="space-y-2">
                <Progress value={percentage} className={isOverBudget ? '[&>div]:bg-destructive' : ''} />
                <div className="flex justify-between text-sm">
                  <span className="text-muted-foreground">
                    Gastado: ${budget.spent.toLocaleString('es-CO')}
                  </span>
                  <span className="font-medium">
                    ${budget.budget.toLocaleString('es-CO')}
                  </span>
                </div>
              </CardContent>
            </Card>
          );
        })}
      </div>
    </div>
  );
}
