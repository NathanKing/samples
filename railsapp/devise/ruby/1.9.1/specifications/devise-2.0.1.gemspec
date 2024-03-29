# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = %q{devise}
  s.version = "2.0.1"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Jos\u00E9 Valim", "Carlos Ant\u00F4nio"]
  s.date = %q{2012-02-09}
  s.description = %q{Flexible authentication solution for Rails with Warden}
  s.email = %q{contact@plataformatec.com.br}
  s.homepage = %q{http://github.com/plataformatec/devise}
  s.require_paths = ["lib"]
  s.rubyforge_project = %q{devise}
  s.rubygems_version = %q{1.7.2}
  s.summary = %q{Flexible authentication solution for Rails with Warden}

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<warden>, ["~> 1.1"])
      s.add_runtime_dependency(%q<orm_adapter>, ["~> 0.0.3"])
      s.add_runtime_dependency(%q<bcrypt-ruby>, ["~> 3.0"])
      s.add_runtime_dependency(%q<railties>, ["~> 3.1"])
    else
      s.add_dependency(%q<warden>, ["~> 1.1"])
      s.add_dependency(%q<orm_adapter>, ["~> 0.0.3"])
      s.add_dependency(%q<bcrypt-ruby>, ["~> 3.0"])
      s.add_dependency(%q<railties>, ["~> 3.1"])
    end
  else
    s.add_dependency(%q<warden>, ["~> 1.1"])
    s.add_dependency(%q<orm_adapter>, ["~> 0.0.3"])
    s.add_dependency(%q<bcrypt-ruby>, ["~> 3.0"])
    s.add_dependency(%q<railties>, ["~> 3.1"])
  end
end
