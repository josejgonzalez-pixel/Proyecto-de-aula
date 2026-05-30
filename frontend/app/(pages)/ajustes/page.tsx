/**
 * @file app/(pages)/ajustes/page.tsx
 * @description Página de ajustes/configuración (mobile)
 */

import { Card, CardContent } from '@/components/ui/card';
import { 
  User, 
  Bell, 
  Shield, 
  Palette, 
  HelpCircle, 
  LogOut,
  ChevronRight 
} from 'lucide-react';
import Link from 'next/link';

const settingsItems = [
  { id: 1, label: 'Perfil', description: 'Información personal', icon: User, href: '#' },
  { id: 2, label: 'Notificaciones', description: 'Alertas y recordatorios', icon: Bell, href: '#' },
  { id: 3, label: 'Seguridad', description: 'Contraseña y autenticación', icon: Shield, href: '#' },
  { id: 4, label: 'Apariencia', description: 'Tema y visualización', icon: Palette, href: '#' },
  { id: 5, label: 'Ayuda', description: 'Soporte y preguntas frecuentes', icon: HelpCircle, href: '#' },
];

export default function AjustesPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Ajustes</h1>
        <p className="text-sm text-muted-foreground">Configura tu aplicación</p>
      </div>

      <Card>
        <CardContent className="p-0">
          {settingsItems.map((item, index) => {
            const Icon = item.icon;
            return (
              <Link
                key={item.id}
                href={item.href}
                className={`flex items-center gap-4 p-4 transition-colors hover:bg-muted ${
                  index !== settingsItems.length - 1 ? 'border-b' : ''
                }`}
              >
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-muted">
                  <Icon className="h-5 w-5 text-muted-foreground" />
                </div>
                <div className="flex-1">
                  <p className="font-medium">{item.label}</p>
                  <p className="text-sm text-muted-foreground">{item.description}</p>
                </div>
                <ChevronRight className="h-5 w-5 text-muted-foreground" />
              </Link>
            );
          })}
        </CardContent>
      </Card>

      <Card className="border-destructive/50">
        <CardContent className="p-0">
          <button className="flex w-full items-center gap-4 p-4 text-destructive transition-colors hover:bg-destructive/10">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-destructive/10">
              <LogOut className="h-5 w-5" />
            </div>
            <span className="font-medium">Cerrar Sesión</span>
          </button>
        </CardContent>
      </Card>
    </div>
  );
}
