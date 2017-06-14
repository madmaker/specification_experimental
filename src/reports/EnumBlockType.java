package reports;

public enum EnumBlockType
{
	NONE {
		public String title(){
			return "-";
		}
	},
	DOCUMENTS {
		public String title(){
			return "Документы";
		}
	},
	COMPLEXES {
		public String title(){
			return "Комплексы";
		}
	},
	ASSEMBLIES {
		public String title(){
			return "Сборочные изделия";
		}
	},
	DETAILS {
		public String title(){
			return "Детали";
		}
	},
	STANDARDS {
		public String title(){
			return "Стандартные изделия";
		}
	},
	OTHERS {
		public String title(){
			return "Прочие изделия";
		}
	},
	MATERIALS {
		public String title(){
			return "Материалы";
		}
	},
	KITS {
		public String title(){
			return "Комплекты";
		}
	};
	
	public abstract String title();
}
