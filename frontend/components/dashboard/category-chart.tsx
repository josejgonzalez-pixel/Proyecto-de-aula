/**
 * @file components/dashboard/category-chart.tsx
 * @description Gráfico circular de gastos por categoría
 * Muestra la distribución de gastos en un donut chart
 */

'use client';

import { PieChart, Pie, Cell, ResponsiveContainer } from 'recharts';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { mockCategoryData } from '@/lib/data';

/**
 * Componente CategoryChart
 * Gráfico de dona que muestra la distribución de gastos por categoría
 * Solo visible en desktop
 */
export function CategoryChart() {
  return (
    <Card>
      <CardHeader className="pb-2">
        <CardTitle className="text-base font-semibold">
          Gastos por Categoría
        </CardTitle>
      </CardHeader>
      <CardContent>
        <div className="flex items-center gap-4">
          {/* Gráfico de dona */}
          <div className="h-[140px] w-[140px] relative">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={mockCategoryData}
                  cx="50%"
                  cy="50%"
                  innerRadius={40}
                  outerRadius={65}
                  paddingAngle={2}
                  dataKey="value"
                >
                  {mockCategoryData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Pie>
              </PieChart>
            </ResponsiveContainer>
          </div>
          
          {/* Leyenda */}
          <div className="flex flex-col gap-2">
            {mockCategoryData.map((category) => (
              <div key={category.name} className="flex items-center gap-2">
                <span
                  className="h-2 w-2 rounded-full"
                  style={{ backgroundColor: category.color }}
                />
                <span className="text-xs text-muted-foreground">
                  {category.name}
                </span>
                <span className="text-xs font-medium">
                  {category.value}%
                </span>
              </div>
            ))}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
