/**
 * @file app/(pages)/informes/page.tsx
 * @description Página de informes y reportes financieros
 */

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Download, FileText, TrendingUp, PieChart } from 'lucide-react';

const reports = [
  { id: 1, title: 'Resumen Mensual', description: 'Visión general de ingresos y gastos del mes', icon: FileText },
  { id: 2, title: 'Tendencias', description: 'Análisis de tendencias de gastos', icon: TrendingUp },
  { id: 3, title: 'Por Categoría', description: 'Desglose detallado por categorías', icon: PieChart },
];

export default function InformesPage() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Informes</h1>
          <p className="text-sm text-muted-foreground">Analiza tu situación financiera</p>
        </div>
        <Button variant="outline">
          <Download className="mr-2 h-4 w-4" />
          Exportar PDF
        </Button>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {reports.map((report) => {
          const Icon = report.icon;
          return (
            <Card key={report.id} className="cursor-pointer transition-shadow hover:shadow-md">
              <CardHeader>
                <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-primary/10">
                  <Icon className="h-6 w-6 text-primary" />
                </div>
              </CardHeader>
              <CardContent>
                <CardTitle className="mb-2 text-lg">{report.title}</CardTitle>
                <p className="text-sm text-muted-foreground">{report.description}</p>
              </CardContent>
            </Card>
          );
        })}
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Resumen del Período</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4 md:grid-cols-3">
            <div className="text-center">
              <p className="text-2xl font-bold text-income">$3,500,000</p>
              <p className="text-sm text-muted-foreground">Ingresos Totales</p>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-expense">$2,250,000</p>
              <p className="text-sm text-muted-foreground">Gastos Totales</p>
            </div>
            <div className="text-center">
              <p className="text-2xl font-bold text-primary">$1,250,000</p>
              <p className="text-sm text-muted-foreground">Balance</p>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
