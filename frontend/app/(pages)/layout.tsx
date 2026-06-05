import { Header } from '../../components/layout/header';
import { Sidebar } from '../../components/layout/sidebar';
import { BottomNav } from '../../components/layout/bottom-nav';

export default function PagesLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-screen flex-col lg:flex-row">
      <Sidebar />
      <div className="flex flex-1 flex-col">
        <Header />
        <main className="flex-1 overflow-auto">
          <div className="mx-auto max-w-7xl p-4 pb-24 lg:p-6 lg:pb-6">
            {children}
          </div>
        </main>
        <BottomNav />
      </div>
    </div>
  );
}