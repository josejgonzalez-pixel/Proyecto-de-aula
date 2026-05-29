/**
 * @file app/(pages)/educacion/page.tsx
 * @description Página de educación financiera con recursos y artículos
 */

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { BookOpen, Video, FileText, Lightbulb } from 'lucide-react';

const resources = [
  { id: 1, title: 'Fundamentos del Ahorro', type: 'Artículo', duration: '5 min', icon: FileText, color: 'bg-chart-1' },
  { id: 2, title: 'Cómo crear un presupuesto', type: 'Video', duration: '10 min', icon: Video, color: 'bg-chart-3' },
  { id: 3, title: 'Inversiones para principiantes', type: 'Curso', duration: '30 min', icon: BookOpen, color: 'bg-chart-2' },
  { id: 4, title: 'Control de deudas', type: 'Guía', duration: '8 min', icon: Lightbulb, color: 'bg-chart-4' },
];

const tips = [
  'Ahorra al menos el 20% de tus ingresos mensuales',
  'Revisa tus gastos semanalmente',
  'Establece metas financieras claras y alcanzables',
  'Crea un fondo de emergencia de 3-6 meses de gastos',
];

export default function EducacionPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Educación Financiera</h1>
        <p className="text-sm text-muted-foreground">Aprende a manejar mejor tu dinero</p>
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        {resources.map((resource) => {
          const Icon = resource.icon;
          return (
            <Card key={resource.id} className="cursor-pointer transition-shadow hover:shadow-md">
              <CardContent className="flex items-center gap-4 p-4">
                <div className={`flex h-12 w-12 items-center justify-center rounded-lg ${resource.color} text-white`}>
                  <Icon className="h-6 w-6" />
                </div>
                <div className="flex-1">
                  <h3 className="font-medium">{resource.title}</h3>
                  <p className="text-sm text-muted-foreground">
                    {resource.type} • {resource.duration}
                  </p>
                </div>
              </CardContent>
            </Card>
          );
        })}
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Lightbulb className="h-5 w-5 text-warning" />
            Consejos del Día
          </CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="space-y-3">
            {tips.map((tip, index) => (
              <li key={index} className="flex items-start gap-2">
                <span className="flex h-5 w-5 items-center justify-center rounded-full bg-primary/10 text-xs font-medium text-primary">
                  {index + 1}
                </span>
                <span className="text-sm">{tip}</span>
              </li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </div>
  );
}
