// app/page.tsx (Página de bienvenida / Landing)
import { Button } from '@/components/ui/button';
import Link from 'next/link';

export default function LandingPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen text-center p-6">
      <h1 className="text-4xl font-bold mb-4">Bienvenido a FinanziApp</h1>
      <p className="text-muted-foreground mb-8">
        La mejor forma de gestionar tus presupuestos de manera inteligente.
      </p>
      <div className="flex gap-4">
        <Link href="/login">
          <Button>Ingresar</Button>
        </Link>
      </div>
    </div>
  );
}